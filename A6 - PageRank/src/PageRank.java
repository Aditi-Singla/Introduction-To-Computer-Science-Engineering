import java.util.*;
public class PageRank{
	public static void main(String[] args){
		double[][] matrix = new double[101][100];
		for (int i=0;i<100;i++){
			int n = 0;
			for (int j=0;j<100;j++){
				Random rn = new Random();
				int num = rn.nextInt(5);
				if (num==0){
					matrix[j][i] = 1;
					n++;
				}	
				else
					matrix[j][i] = 0;
			}
			if (n==0){
				for (int j=0;j<100;j++){
					matrix[j][i] = 1;
				}
				matrix[100][i]=100;
			}
			else
				matrix[100][i]=n;
		}
		for (int i=0;i<100;i++){
			for (int j=0;j<100;j++){
				matrix[i][j] /= matrix[100][j];
			}
		}
		for (int i=0; i<100; i++){
			for (int j=0; j<100; j++){
				matrix[i][j] = 0.85*matrix[i][j] + 0.0015;
			}
		}
		double[] priority = new double[100];
		for (int i=0;i<100;i++){
			priority[i] = 1; 
		}
		// int count = 0;
		while (true){
			//count++;
			double[] temp = priority.clone();
			priority = multiply(matrix,priority);
			if (equals(temp,priority))
				break;
		}	
		System.out.println("");
		for (int i=0;i<100;i++){
			System.out.println(priority[i]+" ");
		}
		// System.out.println("");
		// System.out.println("No. of steps:"+count);
	}
	public static Boolean equals(double[] v1,double[] v2){
		for (int j=0;j<v1.length;j++){
			if (Math.abs(v1[j]-v2[j])>0.000001)
				return false;
		}
		return true;
	}
	public static double[] multiply(double[][] v1,double[] v2){
		double[] ans = new double[100];
		for (int i=0; i<100; i++){
			ans[i] = 0;
			for (int j=0; j<100; j++){
				ans[i] += v1[i][j]*v2[j];
			}
		}
		return ans;
	}
}