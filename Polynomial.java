import java.io.*;
import java.util.Arrays;

public class Polynomial { 
	int exponents[];
	double coefficients[];

	Polynomial() {
		coefficients = null;
		exponents = null;
	}

	Polynomial(double[] c, int[] e) {
		coefficients = c;
		exponents = e;
	}
	
	Polynomial(File f) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String polyline = reader.readLine();
			reader.close();
			
			polyline = polyline.replace("-", "+-");
			String[] terms = polyline.split("[+]");
			
			this.coefficients = new double[terms.length];
			this.exponents = new int[terms.length];
			
			for (int i = 0; i < terms.length; i++) {
				String[] parts = terms[i].split("[x]");
				this.coefficients[i] = Double.parseDouble(parts[0]);
				if (parts.length > 1) {
					this.exponents[i] = Integer.parseInt(parts[1]);					
				} else {
					this.exponents[i] = 0;
				}
			}
		} catch(IOException inputError) {
			System.out.println("Caught IOException when trying to input from file path " + f.getAbsolutePath());
		}
		
	}
	
	public Polynomial add(Polynomial other) {
		
		if (Polynomial.isZero(other) && Polynomial.isZero(this)) {
			return new Polynomial();
		} else if (Polynomial.isZero(other)) {
			Polynomial p = new Polynomial(new double[] {0}, new int[] {0}); 
			return this.add(p);
		} else if (Polynomial.isZero(this)) {
			Polynomial p = new Polynomial(new double[] {0}, new int[] {0}); 
			return other.add(p);
		}
		
		double new_c = 0;
		double[] new_coeff = new double[this.coefficients.length + other.coefficients.length];
		int[] new_exp = new int[this.exponents.length + other.exponents.length];
		boolean[] this_used = new boolean[this.exponents.length];
		boolean[] other_used = new boolean[other.exponents.length];
		
		int new_index = 0;
		for (int i = 0; i < this.exponents.length; i++) {
			if (this_used[i]) continue;
			new_c = this.coefficients[i];
			this_used[i] = true;
			
			// if there is a term within the same polynomial that has the same degree, add the coefficients
			for (int j = 0; j < this.exponents.length; j++) {
				if (!this_used[j] && i != j && this.exponents[i] == this.exponents[j]) {
					new_c += this.coefficients[j];
					this_used[j] = true;
				}
			}
		
			// if there is a term within the other polynomial that has the same degree, add their coefficients
			for (int j = 0; j < other.exponents.length; j++) {
				if (!other_used[j] && this.exponents[i] == other.exponents[j]) {
					new_c += other.coefficients[j];
					other_used[j] = true;
				}
			}
			
			if (new_c != 0) {
				new_coeff[new_index] = new_c;
				new_exp[new_index] = this.exponents[i];
				new_index++;	
			}	
		}

		for (int j = 0; j < other.exponents.length; j++) {
			if (!other_used[j] && other.coefficients[j] != 0) {
				new_coeff[new_index] = other.coefficients[j];
				new_exp[new_index] = other.exponents[j];
				other_used[j] = true;
				for (int k = 0; k < other.exponents.length; k++) {
					if (j != k && !other_used[k] && other.exponents[k] == new_exp[new_index]) {
						new_coeff[new_index] += other.coefficients[k];
						other_used[k] = true;
					}
				}
				if (new_coeff[new_index] != 0)
					new_index++;
			}
		}
		
		double[] return_coefficients = new double[new_index];
		int[] return_exponents = new int[new_index];
		
		return_coefficients = Arrays.copyOfRange(new_coeff, 0, new_index);
		return_exponents = Arrays.copyOfRange(new_exp, 0, new_index);
		
		Polynomial r = new Polynomial(return_coefficients, return_exponents);
		
		return r; 	
	} 
	
	private static boolean isZero(Polynomial other) {
		return other == null || other.coefficients == null || other.exponents == null;
	}
		
	public Polynomial multiply(Polynomial other) {
		// if one of the polynomials is the zero polynomial, return the zero polynomial
		if (Polynomial.isZero(other) || Polynomial.isZero(this)) {
			return new Polynomial();
		}
		
		double[] subresult_coeff = new double[other.coefficients.length];
		int[] subresult_exp = new int[other.coefficients.length];
		Polynomial total = new Polynomial();
		for (int i = 0; i < this.coefficients.length; i++) {
			for (int j = 0; j < other.coefficients.length; j++) {
				subresult_coeff[j] = this.coefficients[i] * other.coefficients[j];
				subresult_exp[j] = this.exponents[i] + other.exponents[j];
			}
			total = total.add(new Polynomial(subresult_coeff, subresult_exp));
		}
		return total;
	}
	
	public double evaluate(double x) {
		double answer = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			answer += this.coefficients[i] * Math.pow(x, this.exponents[i]);
		}
		return answer;
	}
	
	public String p_toString() {
		if (Polynomial.isZero(this)) {
			return "0";
		}
		String r = "";
		for (int j = 0; j < this.coefficients.length; j++) {
			if (r.length() > 0 && this.coefficients[j] >= 0)
				r += "+";
			r += Double.toString(this.coefficients[j]);
			if (this.exponents[j] != 0) r += "x" + Integer.toString(this.exponents[j]);
		}
		return r;
	}
	
	public void saveToFile(String file_name) {
		try {
			FileWriter fw = new FileWriter(file_name, false);
			fw.write(this.p_toString());
			fw.close();
			
		} catch(IOException outputError) {
			System.out.println("Caught IOException when trying to output to file " + file_name);
		}
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0d;
	}

}