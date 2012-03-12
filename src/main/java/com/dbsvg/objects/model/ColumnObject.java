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

/**
 *
 * @author Derrick Bowen <derrickbowen@gmail.com>
 */
public class ColumnObject implements Column {

   String name;
   String nullable;
   String comment;
   int ordinalValue;
   String dataType;
   Table table;

   /**
    * 
    */
   public ColumnObject() {
   }

   /**
    * 
    * @param name
    */
   public ColumnObject(String name) {
      this.name = name;
   }
   /**
    * 
    * @param c
    * @return
    */
   public static Column clone(Column c) {
      Column n = new ColumnObject();
      n.setName(c.getName());
      n.setComment(c.getComment());
      n.setNullable(c.getNullable());
      n.setOrdinalValue(c.getOrdinalValue());
      n.setDataType(c.getDataType());
      n.setTable(c.getTable());
      return n;
   }

   /**
    * 
    * @return
    */
   public ForeignKey transformToFK() {
      ForeignKey n;
      if (this.getClass() == new PrimaryKeyObject().getClass()) {
         n = new PrimaryForeignKey();
         PrimaryKeyObject pk = (PrimaryKeyObject)this;
         pk.cloneTo((PrimaryForeignKey)n);
      } else {
         n = new ForeignKey();
      }
      this.copyTo(n);
      return n;
   }

   /**
    * 
    * @return
    */
   public PrimaryKey transformToPK() {
      PrimaryKey n;
      if (this.getClass() == new PrimaryKeyObject().getClass()) {
         n = new PrimaryForeignKey();
         PrimaryKeyObject pk = (PrimaryKeyObject)this;
         pk.cloneTo((PrimaryForeignKey)n);
      } else {
         n = new PrimaryKeyObject();
      }
      this.copyTo(n);
      return n;
   }
   
   private Column copyTo(Column n) {
      n.setName(this.getName());
      n.setComment(this.getComment());
      n.setNullable(this.getNullable());
      n.setOrdinalValue(this.getOrdinalValue());
      n.setDataType(this.getDataType());
      n.setTable(this.getTable());
      return n;
       
   }

   /**
    * 
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * 
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * 
    * @return
    */
   public String getComment() {
      return comment;
   }

   /**
    * 
    * @param comment
    */
   public void setComment(String comment) {
      this.comment = comment;
   }

   /**
    * 
    * @return
    */
   public String getNullable() {
      return nullable;
   }

   /**
    * 
    * @param nullable
    */
   public void setNullable(String nullable) {
      this.nullable = nullable;
   }

   /**
    * 
    * @return
    */
   public int getOrdinalValue() {
      return ordinalValue;
   }

   /**
    * 
    * @param ordinalValue
    */
   public void setOrdinalValue(int ordinalValue) {
      this.ordinalValue = ordinalValue;
   }

   /**
    * 
    * @return
    */
   public String getDataType() {
      return dataType;
   }

   /**
    * 
    * @param dataType
    */
   public void setDataType(String dataType) {
      this.dataType = dataType;
   }
   
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
