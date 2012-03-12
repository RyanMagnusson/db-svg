/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.objects.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derrick.bowen
 */
public class ColumnObjectTest {

    public ColumnObjectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of clone method, of class ColumnObject.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        Column c = new ColumnObject();
        c.setName("Larry");
        c.setComment("Stooge #1");
        c.setNullable("False");
        c.setOrdinalValue(8);
        c.setDataType("VARCHAR");
        c.setTable(new Table());
        Column result = ColumnObject.clone(c);
        assertTrue(c != result);
        assertEquals(c.getClass(), result.getClass());
        assertEquals(c.getComment(), result.getComment());
        assertEquals(c.getDataType(), result.getDataType());
        assertEquals(c.getName(), result.getName());
        assertEquals(c.getNullable(), result.getNullable());
        assertEquals(c.getOrdinalValue(), result.getOrdinalValue());
        assertEquals(c.getTable(), result.getTable());
    }

    /**
     * Test of transformToFK method, of class ColumnObject.
     */
    @Test
    public void testTransformToFK() {
        System.out.println("transformToFK");
        Column c = new ColumnObject();
        c.setName("Larry");
        c.setComment("Stooge #1");
        c.setNullable("False");
        c.setOrdinalValue(8);
        c.setDataType("VARCHAR");
        c.setTable(new Table());
        ForeignKey result = c.transformToFK();
        ForeignKey fk = new ForeignKey();
        assertTrue(c.getClass() != result.getClass());
        assertTrue(fk.getClass() == result.getClass());
    }

    /**
     * Test of transformToPK method, of class ColumnObject.
     */
    @Test
    public void testTransformToPK() {
        System.out.println("transformToPK");
        Column c = new ColumnObject();
        c.setName("Larry");
        c.setComment("Stooge #1");
        c.setNullable("False");
        c.setOrdinalValue(8);
        c.setDataType("VARCHAR");
        c.setTable(new Table());
        PrimaryKey result = c.transformToPK();
        PrimaryKey pk = new PrimaryKeyObject();
        assertTrue(c.getClass() != result.getClass());
        assertTrue(pk.getClass() == result.getClass());
    }

    /**
     * Test of getName method, of class ColumnObject.
     */
    @Test
    public void testGetSetName() {
        System.out.println("getsetName");
        Column c = new ColumnObject();
        c.setName("Larry");
        String expResult = "Larry";
        String result = c.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getComment method, of class ColumnObject.
     */
    @Test
    public void testGetSetComment() {
        System.out.println("getsetComment");
        ColumnObject instance = new ColumnObject();
        instance.setComment("Stooge #1");
        String expResult = "Stooge #1";
        String result = instance.getComment();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNullable method, of class ColumnObject.
     */
    @Test
    public void testGetSetNullable() {
        System.out.println("getsetNullable");
        ColumnObject instance = new ColumnObject();
        instance.setNullable("False");
        String expResult = "False";
        String result = instance.getNullable();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOrdinalValue method, of class ColumnObject.
     */
    @Test
    public void testGetSetOrdinalValue() {
        System.out.println("getsetOrdinalValue");
        ColumnObject instance = new ColumnObject();
        int ordinalValue = 0;
        instance.setOrdinalValue(ordinalValue);
        int result = instance.getOrdinalValue();
        assertEquals(ordinalValue, result);
    }

    /**
     * Test of getDataType method, of class ColumnObject.
     */
    @Test
    public void testGetSetDataType() {
        System.out.println("getsetDataType");
        String dataType = "VARCHAR";
        ColumnObject instance = new ColumnObject();
        instance.setDataType(dataType);
        String result = instance.getDataType();
        assertEquals(dataType, result);
    }

    /**
     * Test of getTable method, of class ColumnObject.
     */
    @Test
    public void testGetSetTable() {
        System.out.println("getsetTable");
        Table table = new Table("Test Table 1");
        ColumnObject instance = new ColumnObject();
        instance.setTable(table);
        Table result = instance.getTable();
        assertEquals(table, result);
    }

}