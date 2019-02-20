import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class NewsGroupClassifier{
	public static void main (String[] args){
		long startTime = System.currentTimeMillis();
		BufferedReader b = null;
		try {
			String[] stopwords = {"a","an","the","they","these","for","is","are","of","or","and","does","will","whose","that","you","not","your","can","had"};
			Double finalans = 0.0;
			for (int j=0;j<5;j++){
				String textString;
				int totalDocs=0;
				HashMap<String,NewsGroup> classes = new HashMap<String,NewsGroup>();
				Vector<String> vocabulary = new Vector<String>();
				for (int i=0;i<5;i++){
					if (i==j)
						continue;
					b = new BufferedReader(new FileReader("file_"+i+".txt"));
					while ((textString = b.readLine()) != null) {
						String[] l = textString.trim().toLowerCase().split("\\s+",2);
						String head = l[0];
						String tail = l[1];
						totalDocs++;
						if (classes.get(head)==null){
							classes.put(head,new NewsGroup(head));
							classes.get(head).addDoc(tail);
						}
						else
							classes.get(head).addDoc(tail);
					}
				}
				Iterator <String> it1= classes.keySet().iterator();
				while (it1.hasNext ()){
					int p=0;
					String tmp = it1.next();
					NewsGroup n = classes.get(tmp);
					String[] l = n.text.trim().split(" +");
					n.prior = (float)n.no_of_docs/totalDocs;
					for (int i=0;i<l.length;i++){
						if (Arrays.asList(stopwords).contains(l[i]))
							continue;
						else{
							if (n.wordList.get(l[i])==null){
								n.wordList.put(l[i],1.0);
									if (!vocabulary.contains(l[i]))
									vocabulary.add(l[i]);
							}	
							else
								n.wordList.put(l[i],n.wordList.get(l[i])+1);
							p++;
						}
					}
					n.denom = p;
					//System.out.println(p);
				}
				//System.out.println(vocabulary.size());
				int num=totalDocs/4;
				b = new BufferedReader(new FileReader("file_"+j+".txt"));
				int finalScore =0;
				while ((textString = b.readLine()) != null) {
					String[] l = textString.trim().toLowerCase().split("\\s+",2);
					String head1 = l[0];
					String tail1 = l[1];
					String[] expansion =tail1.trim().toLowerCase().split("\\s+");
					Double maxSoFar = Double.MAX_VALUE;
					String maxSoFarName = "";
					Iterator <String> it2= classes.keySet().iterator();
					while (it2.hasNext ()){
						String tmp = it2.next();
						NewsGroup n = classes.get(tmp);
						Double sc = Math.log(n.prior);
						for (int i=0;i<expansion.length;i++){
							if (Arrays.asList(stopwords).contains(expansion[i]))
								continue;
							else{		
								String current = expansion[i];
								if (n.wordList.get(current)!=null)
									sc += Math.log((Double)(n.wordList.get(current)+1)/(n.denom+vocabulary.size()));
								else
									sc += Math.log((Double)1.0/(n.denom+vocabulary.size()));
							}
						}
						if (Math.abs(sc)<maxSoFar){
							maxSoFar = Math.abs(sc);
							maxSoFarName = tmp;
						}			
					}
					if (maxSoFarName.equals(head1))
						finalScore++;
				}
				float accuracy = (float)finalScore/num;
				float acc = accuracy*100;
				System.out.println("The accuracy for set "+(j+1)+" is "+acc+" %.");
				finalans += acc;
			}
			finalans = (Double)finalans/5;
			System.out.println("The average accuracy is "+finalans+" %.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (b != null)
					b.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		long stopTime = System.currentTimeMillis();
    	long elapsedTime = stopTime - startTime;
    	System.out.println("Total time taken is :"+elapsedTime+" ms");
	}
}
