/*
 * Copyright (c) 2015 GraphAware
 *
 * This file is part of GraphAware.
 *
 * GraphAware is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.graphaware.integration.es.plugin.graphbooster;

/**
 *
 * @author ale
 */
public class Neo4JResult
{
  private long nodeId;
  private String uuid;
  private String item;
  private float score;
  public Neo4JResult()
  {
  }

  public long getNodeId()
  {
    return nodeId;
  }
  public void setNodeId(long nodeId)
  {
    this.nodeId = nodeId;
  }
  public String getUuid()
  {
    return uuid;
  }
  public void setUuid(String uuid)
  {
    this.uuid = uuid;
  }
  public String getItem()
  {
    return item;
  }
  public void setItem(String item)
  {
    this.item = item;
  }
  public float getScore()
  {
    return score;
  }
  public void setScore(float score)
  {
    this.score = score;
  }

  
}