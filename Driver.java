import java.io.File;

public class Driver {
	
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		double[] c1 = {6,0,0,5};
		int[] e1 = {4,5,6,7};
		Polynomial p1 = new Polynomial(c1, e1);
		double[] c2 = {0,-2,0,-9};
		int[] e2 = {0,1,2,3};
		Polynomial p2 = new Polynomial(c2, e2);
		
		// TESTING ADDITION
		Polynomial s = p1.add(p2);
		System.out.println("ADDING: " + p1.p_toString() + " with " + p2.p_toString());
		System.out.println("sum: " + s.p_toString());
		
		// ADDITION WITH THE ZERO POLYNOMIAL
		System.out.println("ADDING: " + p.p_toString() + " with " + p1.p_toString());
		p = p.add(p1);
		System.out.println("sum: " + p.p_toString());
		
		// TESTING MULTIPLICATION
		double[] c3 = {1, -5};
		int[] e3 = {1, 0};
		double[] c4 = {1, -5};
		int[] e4 = {1, 0};
		Polynomial p3 = new Polynomial(c3, e3);
		Polynomial p4 = new Polynomial(c4, e4);
		Polynomial product = p3.multiply(p4);
		System.out.println("MULTIPLYING: " + p3.p_toString() + " with " + p4.p_toString());
		System.out.println("product: " + product.p_toString());

		// TESTING FILE INPUT
		File f = new File("./poly_in.txt");
		Polynomial pf = new Polynomial(f);
		System.out.println("Successfully read polynomial from file: " + pf.p_toString());
		
		// TEST FILE OUTPUT
		String file_out = "./poly_out.txt";
		System.out.println("Saving previously calculated product to file: " + file_out);
		product.saveToFile(file_out);
		
		Polynomial ret = new Polynomial(new File(file_out));
		System.out.println("Read back the polynomial written: " + ret.p_toString());
		
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
	}
}