package esgcore.regression;

import esgcore.linalg.Matrix;
import esgcore.linalg.Vector;

public class PolynomialRegression {
	private Matrix regressors;
	private Vector regressand;
	private Vector beta;
	private int order;
	
	// Polynomial of order k
	public PolynomialRegression(Vector regressors, Vector regressand, int k) {
		this.order = k;
		this.regressand = regressand.copy();
		int n = regressors.getDimension();
		double[][] A = new double[n][k+1];
		for(int i=0; i<n; i++)
			for(int j=0; j<k+1; j++)
				A[i][j] = Math.pow(regressors.getEntry(i), j);
		this.regressors = new Matrix(A);
		this.setBeta();
	}
	
	public void setBeta() {
		this.beta = regressors.transpose().multiply(regressors).inverse().multiply(regressors.transpose()).operate(regressand);
	}
	
	public Vector getBeta() {
		return this.beta;
	}
	
	public double predict(double regressors) {
		double[] v = new double[this.order+1];
		for(int j=0; j<this.order+1; j++)
			v[j] = Math.pow(regressors, j);
		return (new Vector(v)).dotProduct(this.beta);
	}
	
	public Vector predict(Vector regressors) {
		return regressors.map(x -> predict(x));
	}

}
