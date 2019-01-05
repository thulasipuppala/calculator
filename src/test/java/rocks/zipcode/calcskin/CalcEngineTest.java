package rocks.zipcode.calcskin;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalcEngineTest {
    CalcEngine testCalc;

    @Before
    public void setUp() throws Exception {
        this.testCalc = new CalcEngine();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSquare(){
        // set up data
        double x = 3.0;

        //invoke method under test
        double actualResult = CalcEngine.square(x);

        //verify result
        double expectResult = 9.0;
        assertEquals(expectResult, actualResult, 0.001);

    }

    @Test
    public void testsquareroot(){
        double x = 25.0;

        double actualResult = CalcEngine.squareroot(x);

        double expectResult = 5.0;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testinverse(){
        double x = 2;

        double actualResult = CalcEngine.inverse(x);

        double expectResult = 0.5;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testinvert(){
        double x = 12.0;

        double actualResult = CalcEngine.invert(x);

        double expectResult = -12.0;
        assertEquals(expectResult, actualResult, 0.001);
    }

/*    @Test
    public void testfindSin(){
        double x = 90.0;

        double actualResult = CalcEngine.findSin(x);

        double expectResult = 0.8939966636;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testfindCos(){
        double x = 45.0;

        double actualResult = CalcEngine.findCos(x);

        double expectResult = 0.52532198881;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testfindTan(){
        double x = 100.0;

        double actualResult = CalcEngine.findTan(x);

        double expectResult = -0.58721391515;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testfindAsin(){
        double x = 0.5;

        double actualResult = CalcEngine.findAsin(x);

        double expectResult = 0.52359877559;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testfindAcos(){
        double x = 1.0;

        double actualResult = CalcEngine.findAcos(x);

        double expectResult = 0;
        assertEquals(expectResult, actualResult, 0.001);
    }

    @Test
    public void testfindAtan(){
        double x = 1.0;

        double actualResult = CalcEngine.findAtan(x);

        double expectResult = 0.78539816339;
        assertEquals(expectResult, actualResult, 0.001);

    }*/

    @Test
    public void testAddition()
    {
        assertEquals(20.0, CalcEngine.addition(15, 5), 0.1);
    }


    @Test
    public void testSubstraction()
    {
        assertEquals(3.0, CalcEngine.subtraction(5, 2), 0.1);


    }

    @Test
    public void testMultiplication(){
        assertEquals(6.0, CalcEngine.multiplication(3, 2), 0.1);
    }

    @Test
    public void testDivision()
    {
        assertEquals(3.0, CalcEngine.division(6, 2), 0.1);
    }

    @Test
    public void testExponent()
    {
        assertEquals(64.0, CalcEngine.exponent(4, 3), 0.1);
    }

}