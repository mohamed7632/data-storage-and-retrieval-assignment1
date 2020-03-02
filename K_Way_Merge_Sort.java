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
		
		RandomAccessFile file = new RandomAccessFile(Inputfilename, "r");
		int file_size = (int)file.length();
		int number_of_runs = (file_size / 8) / runSize;
		String[] runs = new String[number_of_runs];
		file.close();
		
		int pointer = 0;
		for(int i = 0; i < runs.length; i++) {
			runs[i] = "run " + i +".bin";
			RandomAccessFile run_file = new RandomAccessFile("run " + i +".bin", "rws");
			for(int j = 0; j < runSize; j++) {
				RandomAccessFile index_file = new RandomAccessFile(Inputfilename, "r");
				index_file.seek(pointer);
				Record record = read_record(index_file);
				write_record(run_file, record);
				if(index_file.getFilePointer() == index_file.length()) { // end of file
					index_file.close();
					break;
				}
				pointer = (int)index_file.getFilePointer();
				index_file.close();
			}
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
	
	void DoKWayMergeAndWriteASortedFile(String[] SortedRunsNames,int K, String Sortedfilename) throws IOException {
		
	}
	
	public static void main(String[] args) throws IOException {
		/*
		String[] temp = DivideIntputFileIntoRuns("index.bin", 16);
		for(int i = 0; i < temp.length; i++) {
			RandomAccessFile file = new RandomAccessFile(temp[i], "r");
			while(file.getFilePointer() < file.length()) {
				read_record(file).print();
			}
			file.close();
			System.out.println("\n\n\n");
		}
		System.out.println("------------------------------------------------------------------");
		String[] sorted_runs = SortEachRunOnMemoryAndWriteItBack(temp);
		for(int i = 0; i < sorted_runs.length; i++) {
			RandomAccessFile file = new RandomAccessFile(sorted_runs[i], "r");
			while(file.getFilePointer() < file.length()) {
				read_record(file).print();
			}
			file.close();
			System.out.println("\n\n\n");
		}
		*/
	}
}
