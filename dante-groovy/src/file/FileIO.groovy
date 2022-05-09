package file

import java.io.File

class FileIO {
	
	static void main(String[] args) {
//		readFile();
//		readFile2();
		writeFile();
	}
	
	static void readFile() {
		File file = new File('/Users/dante/Documents/Project/java-world/javaworld/dante-groovy/src/file/info.txt');
		println(file.getText());
	}
	
	static void readFile2() {
		File file = new File('/Users/dante/Documents/Project/java-world/javaworld/dante-groovy/src/file/info.txt');
		file.eachLine { 
			line -> println "line : $line"; 
		}
	}
	
	static def writeFile() {
		def file = new File('/Users/dante/Documents/Project/java-world/javaworld/dante-groovy/src/file/out.txt');
		file.withWriter('utf-8') { 
			writer -> writer.writeLine('Groovy 写文件！');
		}
	}
	
}
