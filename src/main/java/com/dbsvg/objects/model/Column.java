/*
 * DB-SVG Copyright 2009 Derrick Bowen
 *
 * This file is part of DB-SVG.
 *
 *   DB-SVG is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   DB-SVG is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with DB-SVG.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package com.dbsvg.objects.model;

import java.io.Serializable;

/**
 *
 * @author Derrick Bowen <derrickbowen@gmail.com>
 */
public interface Column extends Serializable{

   /**
    *
    * @return
    */
   String getDataType();

   /**
    *
    * @return
    */
   String getComment();

   /**
    *
    * @return
    */
   String getName();

   /**
    *
    * @return
    */
   int getOrdinalValue();

   /**
    *
    * @return
    */
   String getNullable();

   /**
    *
    * @param dataType
    */
   void setDataType(String dataType);

   /**
    *
    * @param defaultValue
    */
   void setComment(String comment);

   /**
    *
    * @param name
    */
   void setName(String name);

   /**
    *
    * @param notNull
    */
   void setNullable(String nullable);

   /**
    *
    * @param ordinalValue
    */
   void setOrdinalValue(int ordinalValue);

   /**
    *
    * @return
    */
   ForeignKey transformToFK();

   /**
    *
    * @return
    */
   PrimaryKey transformToPK();

   Table getTable();
   
   void setTable(Table table);

}
