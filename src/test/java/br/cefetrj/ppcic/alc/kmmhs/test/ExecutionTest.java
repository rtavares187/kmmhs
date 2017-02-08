package br.cefetrj.ppcic.alc.kmmhs.test;

import java.io.BufferedReader;
import java.io.FileReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.la4j.matrix.sparse.CRSMatrix;

import cern.colt.matrix.io.MatrixVectorReader;
import br.cefetrj.ppcic.alc.kmmhs.MHSM;
import br.cefetrj.ppcic.alc.kmmhs.impl.MHSMImpl;
import br.cefetrj.ppcic.alc.kmmhs.util.Loader;

/**
 * 
 * @author rtavares
 *
 */
public class ExecutionTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ExecutionTest( String testName ){
    	
        super( testName );
        
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
    	
        return new TestSuite( ExecutionTest.class );
        
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
    	
        assertTrue( true );
        
    }
    
    public static void main(String[] args){
    	
    	try{
    		
    		BufferedReader br = new BufferedReader( new FileReader(Loader.class.getResource("/" + "mcca.mtx").getPath()));
    		MatrixVectorReader mr = new MatrixVectorReader(br);
    		
    		CRSMatrix m = Loader.loadMatrixFromMM("mcca.mtx");
    		
    		CRSMatrix a = Loader.loadMatrixFromCSV("a.txt");
    		
    		CRSMatrix b = Loader.loadMatrixFromCSV("b.txt");
    		
    		MHSM method = new MHSMImpl();
    		method.executar(a, b);
    		
    		/*
    		System.out.println("");
    		System.out.println("Matriz:");
    		System.out.println(m.toString());
    		
    		System.out.println("");
    		System.out.println("A: ");
    		
    		CRSMatrix a = Util.getInstance().getA(m);
    		System.out.println(a.toString());
    		
    		System.out.println("");
    		System.out.println("B: ");
    		
    		CRSMatrix b = Util.getInstance().getB(m);
    		System.out.println(b.toString());
    		
    		System.out.println("");
    		System.out.println("Vetor:");
    		
    		CRSMatrix v = new CRSMatrix(3, 1);
    		
    		v.set(0, 0, 2.00);
    		v.set(1, 0, 5.00);
    		v.set(2, 0, 9.00);
    		
    		CRSMatrix r = v.multiplyByItsTranspose().to(Matrices.CRS);
    		
    		System.out.println(r.toString());
    		
    		System.out.println("");
    		System.out.println("NormaE:");
    		System.out.println(v.euclideanNorm());
    		
    		*/
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
    	
    }
    
}
