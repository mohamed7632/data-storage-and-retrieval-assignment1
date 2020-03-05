import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

public class K_Way_Merge_Sort {
	
	public static Record read_record(RandomAccessFile file) throws IOException {
		return new Record(file.readInt(), file.readInt());
	}
	
	public static void write_record(RandomAccessFile file, Record record) throws IOException {
		file.writeInt(record.getKey());
		file.writeInt(record.getOffset());
	}
	
	public static String[] DivideIntputFileIntoRuns(String Inputfilename, int runSize) throws IOException {
		
		RandomAccessFile file = new RandomAccessFile("index.bin", "r");
		double file_size = (double)file.length();
		double number_of_runs = (file_size / 8) / runSize;
		int nruns = (int)Math.ceil(number_of_runs);
		String[] runs = new String[nruns];
		file.close();
		
		long pointer = 0;
		for(int i = 0; i < runs.length; i++) {
			runs[i] = "run " + i +".bin";
			RandomAccessFile run_file = new RandomAccessFile(runs[i], "rws");
			RandomAccessFile index_file = new RandomAccessFile(Inputfilename, "r");
			index_file.seek(pointer);
			for(int j = 0; j < runSize; j++) {
				Record record = read_record(index_file);
				write_record(run_file, record);
				if(index_file.getFilePointer() == index_file.length()) { // end of file
					pointer = index_file.getFilePointer();
					index_file.close();
					break;
				}
				pointer = index_file.getFilePointer();
			}
			run_file.close();
			index_file.close();
		}
		return runs;
	}
	
	public static String[] SortEachRunOnMemoryAndWriteItBack(String[] RunsFilesNames) throws IOException {
		for(int i = 0; i < RunsFilesNames.length; i++) {
			ArrayList<Record> arr = new ArrayList<>();
			RandomAccessFile file = new RandomAccessFile(RunsFilesNames[i], "rws");
			while(file.getFilePointer() < file.length()) {
				arr.add(read_record(file));
			}
			Collections.sort(arr, new MyComparator());
			file.setLength(0);
			for(int j = 0; j < arr.size(); j++) {
				write_record(file, arr.get(j));
			}
			file.close();
		}
		return RunsFilesNames;
	}
	
	public static void DoKWayMergeAndWriteASortedFile(String[] SortedRunsNames,int K, String Sortedfilename) throws IOException {
		
		// base case (3amalna merge le kol el files khalas w etba2a file wa7d bs w feh kol el records sorted)
		if(SortedRunsNames.length == 1) {
			File file = new File(SortedRunsNames[0]);
			file.renameTo(new File(Sortedfilename));
			return;
		}
		
		ArrayList<String> new_run_files = new ArrayList<>();
		for(int i = 0; i < SortedRunsNames.length; i += K) {
			ArrayList<Pair<String,Long>> files_to_be_merged = new ArrayList<>(); // String: run file name, Long: el pointer bta3 el file dawat
			
			// hena bna5od asamy el filat el hayet3emlha merge w n7otohom f ArrayList esmaha files_to_be_merged
			for(int j = i; j < i + K; j++) {
				files_to_be_merged.add(new Pair<String,Long>(SortedRunsNames[j], 0l));
			}
			
			long size = 0l; //  el variable da beygama3 el sizes bta3t el files eli hayet3mlha merge
			
			String file_name = "merged"; // esm el file eli haykon megam3 el files eli et3amlha merge
			
			for(int j = 0; j < files_to_be_merged.size(); j++) {
				file_name += "(" + files_to_be_merged.get(j).Get_T1() + ")";
				size += new RandomAccessFile(files_to_be_merged.get(j).Get_T1(), "r").length();
			}
			file_name += ".bin";
			new_run_files.add(file_name); /* el file_name da ben7oto fl ArrayList de 3ashan agam3 el run files el gdeeda 
			w a7otohom fel String[] SortedRunsNames wana ba call el function mn gdeed 3ashan ye3ml nafs el kalam le7ad mawsal lel base case */
			
			RandomAccessFile new_run_file = new RandomAccessFile(file_name, "rws"); // el file eli haykon megam3 el files eli et3amlha merge
			
			/* el mafrod el file da yb2a el size bta3o magmo3 size el files eli beyet3mlha merge fa hanefdal ne3ml read record mn kol file
			w nushuf el record sa7eb el key el soghayar w nektbo fl file da, nefdal ne3ml kda le7ad ma yb2a el size bta3o ad magmo3 el files ely
			beye3mlha merge. de lazmt el loop */
			while(new_run_file.length() < size) {
				ArrayList<Pair<Record,Integer>> Records = new ArrayList<>(); /* ArrayList benshel feha el records eli bene2raha wel Integer da 3ashan
				makano fel ArrayList eli esmaha files_to_be_merged 3ashan lama ykon da el record eli shayel asghar key nru7 bl integer as index
				ya3ni w nzawed el pointer bta3 el file 3ashan lama negi ne2ra mn gdeed
				*/
				
				// loop 3al files eli hayet3mlha merge
				for(int j = 0; j < files_to_be_merged.size(); j++) {
					RandomAccessFile file = new RandomAccessFile(files_to_be_merged.get(j).Get_T1(), "r");
					if(files_to_be_merged.get(j).Get_T2() == file.length()) { // de bs 3ashan law el file khalas arenah kolo fa ne skip el file da
						file.close();
						continue;
					}
					
					// bna5od a5r makan we2fna 3ando w nru7lo w ne3ml read record w no7oto fl ArrayList ely esmaha Records
					long pointer = files_to_be_merged.get(j).Get_T2();
					file.seek(pointer);
					Record record = read_record(file);
					Records.add(new Pair<Record,Integer>(record, j));
					file.close();
				}
				
				/* bene3ml sort lel ArrayList Records w na5od awl wa7d feha 3ashan da el record eli shayel 2a2al key w nektbo fl file el gdeed
				w na5od el index w nru7 ne7arak el pointer 3ashan lama negy ne2ra mn gdeed */
				Collections.sort(Records, new Comparator_1());
				Record record_with_minimum_key = Records.get(0).Get_T1();
				int index = Records.get(0).Get_T2();
				write_record(new_run_file, record_with_minimum_key);
				long old_pointer = files_to_be_merged.get(index).Get_T2();
				long new_pointer = old_pointer + 8;
				files_to_be_merged.get(index).Set_T2(new_pointer);
			}
			new_run_file.close();
		}
		
		// hena ba2a ba3d ma 3amlna merge, benesta5dem el ArrayList new_run_files 3ashan n5aly el String[] SortedRunsNames bel files el gdeeda de
		SortedRunsNames = new String[new_run_files.size()];
		for(int i = 0; i < SortedRunsNames.length; i++) {
			SortedRunsNames[i] = new_run_files.get(i);
		}
		DoKWayMergeAndWriteASortedFile(SortedRunsNames, K, Sortedfilename);
	}
	
	public static void main(String[] args) throws IOException {
		
		String[] temp = DivideIntputFileIntoRuns("index.bin", 8);
		for(int i = 0; i < temp.length; i++) {
			RandomAccessFile file = new RandomAccessFile(temp[i], "r");
			while(file.getFilePointer() < file.length()) {
				read_record(file).print();
			}
			file.close();
			System.out.println("\n\n");
		}
		System.out.println("------------------------------------------------------------------");
		String[] sorted_runs = SortEachRunOnMemoryAndWriteItBack(temp);
		for(int i = 0; i < sorted_runs.length; i++) {
			RandomAccessFile file = new RandomAccessFile(sorted_runs[i], "r");
			while(file.getFilePointer() < file.length()) {
				read_record(file).print();
			}
			file.close();
			System.out.println("\n\n");
		}
		
		DoKWayMergeAndWriteASortedFile(sorted_runs, 2, "test.bin");
		
		RandomAccessFile file = new RandomAccessFile("test.bin", "r");
		while(file.getFilePointer() < file.length()) {
			read_record(file).print();
		}
		file.close();
		
	}
}
