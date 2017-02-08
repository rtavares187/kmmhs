package br.cefetrj.ppcic.alc.kmmhs;

import org.la4j.matrix.sparse.CRSMatrix;

/**
 * 
 * @author rtavares
 *
 */
public interface Method {
	
	void executar(CRSMatrix matriz) throws Exception;
	
	void executar(CRSMatrix a, CRSMatrix b) throws Exception;
	
}
