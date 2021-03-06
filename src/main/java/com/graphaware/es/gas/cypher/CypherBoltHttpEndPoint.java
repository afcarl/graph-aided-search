/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.graphaware.es.gas.cypher;

import java.util.HashMap;

import org.elasticsearch.common.settings.Settings;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import static org.neo4j.driver.v1.Config.EncryptionLevel.NONE;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.util.Pair;

public class CypherBoltHttpEndPoint extends CypherEndPoint {

    private boolean encryption = true;

    CypherBoltHttpEndPoint(Settings settings, String neo4jUrl, String neo4jUsername, String neo4jPassword, boolean encryption) {
        super(settings, neo4jUrl, neo4jUsername, neo4jPassword);
        this.encryption = encryption;
    }

    public CypherResult executeCypher(String cypherQuery) {
        return executeCypher(cypherQuery, new HashMap<String, Object>());
    }

    @Override
    public CypherResult executeCypher(String cypherQuery, HashMap<String, Object> parameters) {
        try {
            Driver driver;
            if (encryption) {
                if (getNeo4jUsername() != null) {
                    driver = GraphDatabase.driver(getNeo4jHost(), AuthTokens.basic(getNeo4jUsername(), getNeo4jPassword()));
                } else {
                    driver = GraphDatabase.driver(getNeo4jHost());
                }
            } else {
                if (getNeo4jUsername() != null) {
                    driver = GraphDatabase.driver(getNeo4jHost(), AuthTokens.basic(getNeo4jUsername(), getNeo4jPassword()), Config.build().withEncryptionLevel(NONE).toConfig());
                } else {
                    driver = GraphDatabase.driver(getNeo4jHost(), Config.build().withEncryptionLevel(NONE).toConfig());
                }
            }            
            Session session = driver.session();
            StatementResult response = session.run(cypherQuery, parameters);
            return buildResult(response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private CypherResult buildResult(StatementResult response) {
        CypherResult result = new CypherResult();
        while (response.hasNext()) {
            Record record = response.next();
            ResultRow resultRow = new ResultRow();
            for (Pair<String, Value> fieldInRecord : record.fields()) {
                resultRow.add(fieldInRecord.key(), fieldInRecord.value());
            }
            result.addRow(resultRow);
        }
        return result;
    }

    @Override
    public CypherResult executeCypher(HashMap<String, String> headers, String query, HashMap<String, Object> parameters) {
        return executeCypher(query, parameters);
    }
}
