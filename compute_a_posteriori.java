import java.io.*;

public class compute_a_posteriori {

	public static void main(String args[]) throws IOException {
		String outputFile = "results.txt";
		BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
		int counter[] = new int[2];
		double cherrypc[] = { 1.00, 0.75, 0.50, 0.25, 0 };
		double limepc[] = { 0, 0.25, 0.50, 0.75, 1.00 };
		int prior[] = { 10, 20, 40, 20, 10 };
		output.write("Observation Sequence Q: " + args[0]);
		output.write("\r\n");
		int leng = args[0].length();
		output.write("Length of Q: " + leng);
		output.write("\r\n");
		for (int i = 0; i < leng; i++) {
			if (args[0].charAt(i) == 'C' || args[0].charAt(i) == 'c') {
				counter[0]++;
			} else if (args[0].charAt(i) == 'L' || args[0].charAt(i) == 'l') {
				counter[1]++;
			} else {
				System.out.println("Invalid Command Line Arguments");
				break;
			}
		}
		double prob[] = new double[prior.length];
		double sum_of_prob = 0.00000;
		for (int j = 0; j < prob.length; j++) {
			prob[j] = Math
					.round(Math.pow(limepc[j], counter[1]) * Math.pow(cherrypc[j], counter[0]) * prior[j] * 100000.0);
			prob[j] = prob[j] / 100000.0;
			sum_of_prob = sum_of_prob + prob[j];
		}
		sum_of_prob = 1 / sum_of_prob;
		for (int k = 0; k < prob.length; k++) {
			prob[k] = Math.round(sum_of_prob * prob[k] * 100000.0);
			prob[k] = prob[k] / 100000.0;
			output.write("P(h" + (k + 1) + "|Q) = " + prob[k]);
			output.write("\r\n");
		}
		double next_prob[] = new double[2];
		for (int i = 0; i < prob.length; i++) {
			next_prob[0] = next_prob[0] + (cherrypc[i] * prob[i]);
			next_prob[1] = next_prob[1] + (limepc[i] * prob[i]);
		}
		for (int j = 0; j <= 1; j++) {
			next_prob[j] = Math.round(next_prob[j] * 100000.0);
			next_prob[j] = next_prob[j] / 100000.0;
		}
		output.write("Probability that the next candy we pick will be C, given Q: " + next_prob[0]);
		output.write("\r\n");
		output.write("Probability that the next candy we pick will be L, given Q: " + next_prob[1]);
		output.close();
	}
}