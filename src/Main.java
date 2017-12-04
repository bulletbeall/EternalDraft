import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;


public class Main {
	public static int checkSum1 = 0;
	public static int checkSum2 = 0;
	public static void main(String[] args) throws IOException{
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		String line = "";	
		String cardString = "";
		
		makeWantFormatted();
		makeWantSorted();
		makeWantRemoveExtra();
		makeHaveFormatted();
		makeHaveSorted();
		makeNeed();
		
		while (cardString != "exit"){
			System.out.print("Enter Card Name: ");
			cardString = (br1.readLine());
			String csvFile = System.getProperty("user.dir")+"/CSVs/Need.csv";
			
		}
	}

	public static void makeWantFormatted() throws IOException{
		int i=0;
		String[] arr1 = new String[800];
		String[] arr2 = new String[800];
		String stringFinal = "";
		String line = "";	
		String csvFile = System.getProperty("user.dir")+"/CSVs/Want_raw.csv";
		BufferedReader br = new BufferedReader(new FileReader(csvFile)); 
		String fileName = System.getProperty("user.dir")+"/CSVs/Want_formatted.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();

		while ((line = br.readLine()) != null) {
			// use comma as separator
			arr1 = null;
			arr2 = null;
			if(line.contains("---------------------------------------")){
				//Do nothing
				i++;
			}
			else{
				line = line.replace(",", "");
				line = line.replace("\"", "");
				arr1 = line.split( "\\)" );
				stringFinal = arr1[0];
				arr1 = stringFinal.split( "\\(" );
				stringFinal = arr1[0] + "," + arr1[1].split("#")[0] + "," + arr1[1].split("#")[1];
				arr2 = stringFinal.split(" ");
				stringFinal = arr2[0] + "," +stringFinal.substring(1);
				stringFinal = stringFinal.split(",")[2].trim() + "," + stringFinal.split(",")[3].trim() + "," 
						+ stringFinal.split(",")[0].trim() + "," + stringFinal.split(",")[1].trim(); 

				if(stringFinal.contains("Fire Sigil")){
					stringFinal = "Set1,1,10,Fire Sigil";
				}
				if(stringFinal.contains("Time Sigil")){
					stringFinal = "Set1,63,10,Time Sigil";
				}
				if(stringFinal.contains("Shadow Sigil")){
					stringFinal = "Set1,249,10,Shadow Sigil";
				}
				if(stringFinal.contains("Justice Sigil")){
					stringFinal = "Set1,126,10,Justice Sigil";
				}
				if(stringFinal.contains("Primal Sigil")){
					stringFinal = "Set1,187,10,Primal Sigil,";
				}

				checkSum1 = checkSum1 + Integer.parseInt(stringFinal.split(",")[2]); 

				builder.append(stringFinal+"\n");
				i++;
				if (i>=799) break;

			}
		}
		pw.write(builder.toString());
		pw.close();
		System.out.println("done! Checksum = " + checkSum1);
	}

	public static void makeWantSorted() throws IOException{
		String[] allWant = new String[800];
		String line = "";	
		String csvFile = System.getProperty("user.dir")+"/CSVs/Want_formatted.csv";
		BufferedReader br = new BufferedReader(new FileReader(csvFile)); 
		String fileName = System.getProperty("user.dir")+"/CSVs/Want_sorted.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();

		int i=0;
		while ((line = br.readLine()) != null) {
			allWant[i] = line;
			i++;
			if (i>=799) break;
		}
		String[] allWant2 = new String[i];
		i=0;
		while(i<allWant2.length){
			allWant2[i] = allWant[i];
			i++;
		}
		sortStringBubble(allWant2);

		i=0;
		while (i<allWant2.length){
			builder.append(allWant2[i]+"\n");
			i++;
		}
		pw.write(builder.toString());
		pw.close();
		System.out.println("done!");
	}

	public static void makeWantRemoveExtra() throws IOException{
		String[] allWant = new String[800];
		String[] arr1 = new String[5];
		String[] arr2 = new String[5];
		String line = "";	
		String csvFile = System.getProperty("user.dir")+"/CSVs/Want_sorted.csv";
		BufferedReader br = new BufferedReader(new FileReader(csvFile)); 
		String fileName = System.getProperty("user.dir")+"/CSVs/Want_noextra.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();

		int i=0;
		while ((line = br.readLine()) != null) {
			allWant[i] = line;
			i++;
			if (i>=799) break;
		}
		String[] allWant2 = new String[i];
		i=0;
		while(i<allWant2.length){
			allWant2[i] = allWant[i];
			i++;
		}
		int k = 0;
		while (k<800){
			i=0;
			while(i<allWant2.length-1){
				if(allWant2[i].isEmpty()){
					i++;
				} else{
					arr1 = allWant2[i].split(",");
					arr2 = allWant2[i+1].split(",");
					if (arr1[0].matches(arr2[0])){
						if(arr1[1].matches(arr2[1])){
							allWant2[i] = arr1[0]+","+arr1[1]+","+(Math.max(Integer.parseInt(arr1[2]),Integer.parseInt(arr2[2])))+","+arr1[3];				
							allWant2[i+1] = "";
						}
					}
					i++;
				}
			}
			i=0;
			while(i<allWant2.length-1){
				if(allWant2[i].isEmpty()){
					allWant2[i] = allWant2[i+1];
					allWant2[i+1] = "";
				}

				i++;
			}
			k++;
		}
		i=0;
		while (i<allWant2.length){
			builder.append(allWant2[i]+"\n");
			checkSum2 = checkSum2 + Integer.parseInt(allWant[i].split(",")[2]); 
			i++;
		}
		pw.write(builder.toString());
		pw.close();
		System.out.println("done! Checksum = " + checkSum2);
	}

	public static void makeHaveFormatted() throws IOException{
		int i=0;
		String[] arr1 = new String[800];
		String[] arr2 = new String[800];
		String stringFinal = "";
		String line = "";	
		String csvFile = System.getProperty("user.dir")+"/CSVs/Have_raw.csv";
		BufferedReader br = new BufferedReader(new FileReader(csvFile)); 
		String fileName = System.getProperty("user.dir")+"/CSVs/Have_formatted.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();

		while ((line = br.readLine()) != null) {
			// use comma as separator
			arr1 = null;
			arr2 = null;
			if(line.contains("---------------------------------------") || line.isEmpty()){
				//Do nothing
				i++;
			}
			else{
				line = line.replace(",", "");
				line = line.replace("\"", "");
				arr1 = line.split( "\\)" );
				stringFinal = arr1[0];
				arr1 = stringFinal.split( "\\(" );
				stringFinal = arr1[0] + "," + arr1[1].split("#")[0] + "," + arr1[1].split("#")[1];
				arr2 = stringFinal.split(" ");
				stringFinal = arr2[0] + "," +stringFinal.substring(1);
				stringFinal = stringFinal.split(",")[2].trim() + "," + stringFinal.split(",")[3].trim() + "," 
						+ stringFinal.split(",")[0].trim() + "," + stringFinal.split(",")[1].trim(); 

				if(stringFinal.contains("Fire Sigil")){
					stringFinal = "Set1,1,200,Fire Sigil";
				}
				if(stringFinal.contains("Time Sigil")){
					stringFinal = "Set1,63,200,Time Sigil";
				}
				if(stringFinal.contains("Shadow Sigil")){
					stringFinal = "Set1,249,200,Shadow Sigil";
				}
				if(stringFinal.contains("Justice Sigil")){
					stringFinal = "Set1,126,200,Justice Sigil";
				}
				if(stringFinal.contains("Primal Sigil")){
					stringFinal = "Set1,187,200,Primal Sigil,";
				}

				builder.append(stringFinal+"\n");
				i++;
				if (i>=799) break;

			}
		}
		pw.write(builder.toString());
		pw.close();
		System.out.println("done!");
	}

	public static void makeHaveSorted() throws IOException{
		String[] allWant = new String[800];
		String line = "";	
		String csvFile = System.getProperty("user.dir")+"/CSVs/Have_formatted.csv";
		BufferedReader br = new BufferedReader(new FileReader(csvFile)); 
		String fileName = System.getProperty("user.dir")+"/CSVs/Have_sorted.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();

		int i=0;
		while ((line = br.readLine()) != null) {
			allWant[i] = line;
			i++;
			if (i>=799) break;
		}
		String[] allWant2 = new String[i];
		i=0;
		while(i<allWant2.length){
			allWant2[i] = allWant[i];
			i++;
		}
		sortStringBubble(allWant2);

		i=0;
		while (i<allWant2.length){
			builder.append(allWant2[i]+"\n");
			i++;
		}
		pw.write(builder.toString());
		pw.close();
		System.out.println("done!");
	}

	public static void makeNeed() throws IOException{
		String[] allWantTemp = new String[800];
		String[] allHaveTemp = new String[800];
		String[] allNeed = new String[800];
		String line = "";	
		String csvFile1 = System.getProperty("user.dir")+"/CSVs/Want_noextra.csv";
		String csvFile2 = System.getProperty("user.dir")+"/CSVs/Have_sorted.csv";
		BufferedReader br1 = new BufferedReader(new FileReader(csvFile1)); 
		BufferedReader br2 = new BufferedReader(new FileReader(csvFile2)); 
		String fileName = System.getProperty("user.dir")+"/CSVs/Need.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();

		int i=0;
		while ((line = br2.readLine()) != null) {
			allHaveTemp[i] = line;
			i++;
			if (i>=799) break;
		}
		String[] allHave = new String[i];
		i=0;
		while (i<allHave.length) {
			allHave[i] = allHaveTemp[i];
			i++;
		}

		i=0;
		int l=0;
		while ((line = br1.readLine()) != null) {
			if(line.isEmpty()){
				i++;
			} else{
				if (checkDontHave(line,allHave)){
					allNeed[l] = line;
					l++;
				}
				i++;
			}
		}

		i=0;
		while (i<allNeed.length){
			builder.append(allNeed[i]+"\n");
			i++;
		}
		pw.write(builder.toString());
		pw.close();
		System.out.println("done!");
	}

	public static boolean checkDontHave(String want, String[] have){
		int i = 0;
		int numCardsWant = Integer.parseInt(want.split(",")[2]);
		String cardNameWant = want.split(",")[3];
		int numCardsHave = 0;
		String cardNameHave = "";
		while(i<have.length){
			numCardsHave = Integer.parseInt(have[i].split(",")[2]);
			cardNameHave = have[i].split(",")[3];
			if(cardNameWant.matches(cardNameHave)){
				if(numCardsWant <= numCardsHave){
					return false;
				} else{
					return true;
				}
			}	
			i++;
		}
		return true;
	}

	public static boolean checkIfNeed(String want, String csvFile){
		int i = 0;
		int numCardsWant = Integer.parseInt(want.split(",")[2]);
		String cardNameWant = want.split(",")[3];
		int numCardsHave = 0;
		String cardNameHave = "";
		
		BufferedReader br2 = new BufferedReader(new FileReader(csvFile));
		
		int i=0;
		int l=0;
		while ((line = br2.readLine()) != null) {
			if(line.isEmpty()){
				i++;
			} else{
				if (checkDontHave(line,allHave)){
					allNeed[l] = line;
					l++;
				}
				i++;
			}
		}
	}

	public static void sortStringBubble( String  x [ ] )
	{
		int j;
		boolean flag = true;  // will determine when the sort is finished
		String temp;

		while ( flag )
		{
			flag = false;
			for ( j = 0;  j < x.length - 1;  j++ )
			{
				if ( x [ j ].compareToIgnoreCase( x [ j+1 ] ) > 0 )
				{                                             // ascending sort
					temp = x [ j ];
					x [ j ] = x [ j+1];     // swapping
					x [ j+1] = temp; 
					flag = true;
				} 
			} 
		} 
	} 

}