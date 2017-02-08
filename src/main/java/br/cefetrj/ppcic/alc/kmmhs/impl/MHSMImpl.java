package br.cefetrj.ppcic.alc.kmmhs.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.la4j.Matrices;
import org.la4j.matrix.sparse.CRSMatrix;

import br.cefetrj.ppcic.alc.kmmhs.MHSM;
import br.cefetrj.ppcic.alc.kmmhs.util.Util;

/**
 * 
 * @author rtavares
 *
 */
public class MHSMImpl implements MHSM {

	private static final double e = 0.01;
	
	private static final int t = 1;
	
	private static final int maxIter = 1000000000;
	
	private static final boolean DEBUG = false;
	
	public void executar(CRSMatrix matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public void executar(CRSMatrix a, CRSMatrix b) throws Exception {
		
		getX(a, b);
		
	}
	
	public CRSMatrix getX(CRSMatrix matriz){
		
		System.out.println("Matir Original: ");
		System.out.println(matriz.toString());
		
		CRSMatrix a = Util.getInstance().getA(matriz);
		CRSMatrix b = Util.getInstance().getB(matriz);
		
		return getX(a, b);
		
	}
	
	public CRSMatrix getX(CRSMatrix a, CRSMatrix b){
		
		Date ini = new Date();
		
		CRSMatrix x = new CRSMatrix(a.columns(), 1);
		CRSMatrix aX = a.multiply(x).to(Matrices.CRS);
		
		CRSMatrix r = b.subtract(aX).to(Matrices.CRS);
		CRSMatrix s = r;
		
		double n = 0;
		
		int iteracao = 0;
		
		while(!convergeSolucao(s, iteracao) && iteracao <= maxIter){
			
			iteracao++;
			
			CRSMatrix aS = a.multiply(s).to(Matrices.CRS);
			
			double l = r.transpose().multiplyByItsTranspose().get(0, 0) / s.transpose().multiply(aS).get(0, 0);
			
			CRSMatrix lS = s.multiply(l).to(Matrices.CRS);
			
			x = x.add(lS).to(Matrices.CRS);
			
			CRSMatrix lAS = aS.multiply(l).to(Matrices.CRS);
			
			CRSMatrix rA = r;
			
			r = r.subtract(lAS).to(Matrices.CRS);
			
			CRSMatrix y = r.subtract(rA).to(Matrices.CRS);
			
			double lambda = 1;
			double beta = 1;
			
			if(r.transpose().multiply(s).get(0, 0) > 0){
				
				lambda = 1 + ((r.transpose().multiply(s).get(0, 0) / s.transpose().multiply(y).get(0, 0)) * (r.transpose().multiply(y).get(0, 0) / r.euclideanNorm()));
				
				double betaDHS = -1 * (rA.transpose().multiply(s).get(0, 0) / s.transpose().multiply(y).get(0, 0));
				
				double min = rA.euclideanNorm() < n ? rA.euclideanNorm() : n;
				
				double nk = -1 / (s.euclideanNorm() * min);
				
				double betaD = betaDHS > n ? betaDHS : n;
				
				beta = betaD;
				
				n = nk;
				
			}else{
				
				double betaHS = -1 * t * ((y.euclideanNorm() * r.transpose().multiply(s).get(0, 0)) / Math.pow(s.transpose().multiply(y).get(0, 0), 2));
				
				beta = betaHS;
				
			}
			
			s = r.multiply(lambda).add(s.multiply(beta)).to(Matrices.CRS);
			
			if(DEBUG){
				
				System.out.println("");
				System.out.println("Iteracao: " + iteracao);
			
				System.out.println("");
				System.out.println("x parcial ( " + iteracao + " ):");
				System.out.println(x.toString());
				
			}
			
		}
		
		Date fim = new Date();
		
		String tempo = String.format("%d minutos", 
			    TimeUnit.MILLISECONDS.toMinutes(fim.getTime() - ini.getTime()));
		
		System.out.println("");
		System.out.println("Tempo: " + tempo);
		System.out.println("Iterações: " + iteracao);
		
		System.out.println("");
		System.out.println("Solução vetor X: ");
		System.out.println(x.toString());
		
		return x;
		
	}
	
	public boolean convergeSolucao(CRSMatrix r, int iteracao) {
		
		if(iteracao == 0)
			return false;
		
		double soma = 0;
		
		for(int i = 0; i < r.rows(); i++){
			
			soma += Math.pow(r.get(i, 0), 2);
			
		}
		
		soma = Math.sqrt(soma);
		
		if(soma < e)
			return true;
		
		return false;
		
	}
	
}
