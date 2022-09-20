public class Polynomial { 
	double coefficients[];
	Polynomial() {
		coefficients = new double[]{0};
	}
	Polynomial(double[] c) {
		coefficients = c;
	}
	public Polynomial add(Polynomial other) {
		int len = Math.max(this.coefficients.length, other.coefficients.length);
		double[] newCoefficients = new double[len];
		for (int i = 0; i < len; i++) {
			double a = i >= this.coefficients.length ? 0 : this.coefficients[i];
			double b = i >= other.coefficients.length ? 0 : other.coefficients[i];
			newCoefficients[i] = a + b;
		}
		return new Polynomial(newCoefficients); 	
	} 
	public double evaluate(double x) {
		double answer = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			answer += this.coefficients[i] * Math.pow(x, i);
		}
		return answer;
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0d;
	}

}