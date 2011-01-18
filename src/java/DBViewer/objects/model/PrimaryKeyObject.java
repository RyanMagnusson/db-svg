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
package DBViewer.objects.model;

/**
 *
 * @author Derrick Bowen <derrickbowen@gmail.com>
 */
public class PrimaryKeyObject extends ColumnObject implements PrimaryKey {
 boolean autoIncrement;

   /**
    * 
    */
   public PrimaryKeyObject() {
   }
   
   /**
    * 
    * @param name
    */
   public PrimaryKeyObject(String name) {
      super.name = name;
   }
   
   /**
    * 
    * @return
    */
   public boolean isAutoIncrement() {
      return autoIncrement;
   }

   /**
    * 
    * @param autoIncrement
    */
   public void setAutoIncrement(boolean autoIncrement) {
      this.autoIncrement = autoIncrement;
   }
   
   public PrimaryKey cloneTo(PrimaryKey pk) {
      return pk;
   }
}
