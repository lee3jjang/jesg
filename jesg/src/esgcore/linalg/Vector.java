package esgcore.linalg;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Vector {
	private double[] data;

	// Initialization with data
	public Vector(double[] data) {
		int n = data.length;
		this.data = new double[n];
		for (int i=0; i<n; i++)
				this.data[i] = data[i];
	}
	
	// Set Entry
	public void setEntry(int i, double value) {
		this.data[i] = value;
	}
	
	// Set
	public void set(double value) {
		int n = this.data.length;
		for(int i=0; i<n; i++)
			this.data[i] = value;
	}
	
	// Add to Entry
	public void addToEntry(int i, double value) {
		this.data[i] += value;
	}
	
	// Get Copy
	public Vector copy() {
		return new Vector(this.data.clone());
	}
	
	// Get Entry
	public double getEntry(int i) {
		return this.data[i];
	}
	
	// Get Data
	public double[] getData() {
		return this.data.clone();
	}
	
	// Get Subvector
	public Vector getSubvector(int r, int s) {
		int n = s-r;
		double[] w = new double[n];
		for(int i=0; i<n; i++)
			w[i] = this.data[r+i];
		return new Vector(w);
	}
	
	// Set Subvector
	public void setSubvector(int r, Vector v) {
		int n = v.getDimension();
		for(int i=0; i<n; i++)
			this.data[r+i] = v.getEntry(i);
	}
	
	// Insert Element
	public void insertElement(int k, double value) {
		int n = this.data.length;
		double[] w = new double[n+1];
		for(int i=0; i<k; i++)
			w[i] = this.data[i];
		w[k] = value;
		for(int i=k+1; i<n+1; i++)
			w[i] = this.data[i-1];
		this.data = w;
	}
	
	// Delete Element
	public void deleteElement(int k) {
		int n = this.data.length;
		double[] w = new double[n-1];
		for(int i=0; i<k; i++)
			w[i] = this.data[i];
		for(int i=k; i<n-1; i++)
			w[i] = this.data[i+1];
		this.data = w;
	}
	
	// Get Dimension
	public int getDimension() {
		return this.data.length;
	}
	
	// Diagonal Matrix of Vector
	public Matrix diag() {
		int n = this.data.length;
		double[][] B = new double[n][n];
		for(int i=0; i<n; i++)
			B[i][i] = this.data[i];
		return new Matrix(B);
	}
	
	// Vector Addition
	public Vector add(Vector v) {
		int n = v.getData().length;
		double[] w = new double[n];
		for(int i=0; i<n; i++)
				w[i] = this.data[i] + v.getEntry(i);
		return new Vector(w);
	}
	
	// Vector Subtraction
	public Vector subtract(Vector v) {
		return this.add(v.scalarMultiply(-1.));
	}
	
	// Power of Vector
	public Vector power(int p) {
		return this.map(x -> Math.pow(x, p));
	}
	
	// Scalar Multiplication of Vector
	public Vector scalarMultiply(double c) {
		return this.map(x -> x*c);
	}
	
	// Scalar Addition of Vector
	public Vector scalarAdd(double c) {
		return this.map(x -> x+c);
	}
	
	// Element-Wise Vector Multiplication
	public Vector eleMultiply(Vector v) {
		int n = v.data.length;
		double[] w = new double[n];
		for(int i=0; i<n; i++)
				w[i] = this.data[i] * v.getEntry(i); 
		return new Vector(w);
	}
	
	// Element-Wise Vector Division
	public Vector eleDivide(Vector v) {
		int n = v.data.length;
		double[] w = new double[n];
		for(int i=0; i<n; i++)
				w[i] = this.data[i] / v.getEntry(i); 
		return new Vector(w);
	}
	
	// Print Vector
	public void print() {
		int n = this.data.length;
		System.out.println("===print===");
		for (int i=0; i<n; i++)
			System.out.print(this.data[i]+" ");
		System.out.println();
	}
	
	// Dot Product
	public double dotProduct(Vector v) {
		int n = this.data.length;
		double value = 0.;
		for (int i=0; i<n; i++)
				value += this.data[i] * v.getEntry(i);
		return value;
	}
	
	// Outer Product
	public Matrix outerProduct(Vector v) {
		int m = this.data.length;
		int n = v.getData().length;
		double[][] B = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = this.data[i] * v.getEntry(j);
		return new Matrix(B);
	}
	
	// Norm
	public double getNorm() {
		return Math.pow(this.dotProduct(this), 0.5);
	}
	
	// Distance
	public double getDistance(Vector v) {
		return this.subtract(v).getNorm();
	}
	
	// Map
	public Vector map(UnaryOperator<Double> fn) {
		int n = this.data.length;
		double[] v = new double[n];
		for(int i=0; i<n; i++)
			v[i] = fn.apply(this.data[i]);
		return new Vector(v);
	}
	
	// Binary Map
	public Vector binaryMap(Vector v, BinaryOperator<Double> fn) {
		int n = this.data.length;
		double[] w = new double[n];
		for(int i=0; i<n; i++)
			w[i] = fn.apply(this.data[i], v.getEntry(i));
		return new Vector(w);
	}
	
	// Cumulative Map
	public Vector cumulativeMap(BinaryOperator<Double> fn) {
		int n = this.data.length;
		double[] v = new double[n];
		v[0] = this.data[0];
		for(int i=1; i<n; i++)
			v[i] = fn.apply(v[i-1], this.data[i]);
		return new Vector(v);
	}
	
	// Cumulative Sum
	public Vector cumulativeSum() {
		return this.cumulativeMap((x, y) -> x+y); 
	}
	
	// Cumulative Product
	public Vector cumulativeProduct() {
		return this.cumulativeMap((x, y) -> x*y); 
	}
	
	// Increment
	public Vector	increment() { 
		int n = this.data.length;
		double[] v = new double[n];
		v[0] = this.data[0];
		for(int i=1; i<n; i++)
			v[i] = this.data[i] - this.data[i-1];
		return new Vector(v);
	}

	// Growth
	public Vector growth() { 
		int n = this.data.length;
		double[] v = new double[n];
		v[0] =  this.data[0];
		for(int i=1; i<n; i++)
			v[i] = this.data[i] / this.data[i-1];
		return new Vector(v);
	}
	
	// Transpose
	public Matrix transpose() {
		int n = this.data.length;
		double[][] B = new double[1][n];
		for (int i=0; i<n; i++)
				B[0][i] = this.data[i];
		return new Matrix(B);
	}
	
	// Sum
	public double sum() {
		int n = this.data.length;
		double value = 0.;
		for(int i=0; i<n; i++)
			value += this.data[i];
		return value;
	}
	
	// Mean
	public double mean() {
		return this.sum()/this.data.length;
	}
	
	// Standard Deviation
	public double std() {
		int n = this.getDimension();
		double mean = this.mean();
		double value = 0.;
		for(int i=0; i<n; i++)
			value += Math.pow(this.data[i] - mean, 2);
		value /= n-1;
		return Math.sqrt(value);
	}
	
	// Concatenate
	public Vector concatenate(Vector v) {
		int n = this.data.length;
		int m = v.getDimension();
		double[] w = new double[n+m];
		for(int i=0; i<n; i++)
			w[i] = this.data[i];
		for(int i=0; i<m; i++)
			w[m+i] = v.getEntry(i);
		return new Vector(w);
	}
	
	// double to String
	public StringVector toStringVector() {
		int n = this.data.length;
		String[] v = new String[n];
		for(int i=0; i<n; i++)
			v[i] = String.valueOf(this.data[i]);
		return new StringVector(v);
	}
	
	// Vector to Matrix
	public Matrix toMatrix(int n) {
		int m = this.data.length/n;
		if(this.data.length%n != 0)
			throw new RuntimeException("��ȿ���� ���� �� �����Դϴ�.");
		double[][] A = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				A[i][j] = this.data[n*i+j];
		return new Matrix(A);
	}
	
	// Rank
	public Vector rank() {
		int n = this.data.length;
		int[] v = new int[n];
		for(int i=0; i<n; i++) {
			v[i] = 0;
			for(int j=0; j<n; j++)
				if(this.data[i] < this.data[j]) v[i]++;
		}
		
		double[] w = new double[n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(v[i] == j) w[i] = j;
			}
		}
		return new Vector(w);
	}
	
	
	/* Vector Utilities */
	// Vector of 1...n
	public static Vector createRangeVector(int n) {
		double[] v = new double[n];
		for(int i=0; i<n; i++)
			v[i] = i;
		return new Vector(v);
		
	}
	
	// Unit Vector
	public static Vector createUnitVector(int i, int n) {
		double[] v = new double[n];
		v[i] = 1.;
		return new Vector(v);
	}
	
	// Zero Vector of n	
	public static Vector createZeroVector(int n) {
		return new Vector(new double[n]);
	}
	
	// Vector of Ones of n
	public static Vector createOneVector(int n) {
		double[] v = new double[n];
		for(int i=0; i<n; i++)
				v[i] = 1.;
		
		
		return new Vector(v);
	}
	
}
