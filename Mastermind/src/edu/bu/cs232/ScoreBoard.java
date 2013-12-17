package edu.bu.cs232;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoard {

	protected static final String DEFAULT_FILE_PATH = "scoreboard.dat";
	private ArrayList<ScoreEntry> scores;
	public ScoreBoard() {
		this.scores = new ArrayList<ScoreEntry>();
	}

	public static ScoreBoard fromFile() throws IOException, FileNotFoundException {
		return ScoreBoard.fromFile(ScoreBoard.DEFAULT_FILE_PATH);
	}
	public static ScoreBoard fromFile(String path) throws IOException, FileNotFoundException {
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(path);
			return ScoreBoard.fromStream(fileInput);
		} finally {
			fileInput.close();
		}
	}
	public static ScoreBoard fromStream(InputStream fileInput) throws IOException, FileNotFoundException {
		ScoreBoard result = new ScoreBoard();
		ObjectInputStream objectInput = null;
		try {
			objectInput = new ObjectInputStream(fileInput);
			int totalItemCount = 0, checkItemCount = 0;
			try {
				totalItemCount = objectInput.readInt();
				for (int i = 0; i < totalItemCount; ++i) {
					result.addEntry((ScoreEntry) objectInput.readObject());
				}
				checkItemCount = objectInput.readInt();
			} catch (IOException ex) {
				throw(ex);
			} catch(Exception ex) {
				;
			}
			if (totalItemCount != checkItemCount) {
				throw new IllegalStateException("Corrupt save file.");
			}
		} finally {
			try {
				objectInput.close();
			} catch (IOException e) {
				;
			}
		}
		
		return result;
	}
	
	public void addEntry(ScoreEntry entry) {
		this.scores.add(entry);
		Collections.sort(this.scores);
	}

	public int saveToFile() throws FileNotFoundException, IOException {
		return this.saveToFile(ScoreBoard.DEFAULT_FILE_PATH);
	}
	public int saveToFile(String path) throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = null;
		try {
			fileOutput = new FileOutputStream(path, false);
			return this.saveToStream(fileOutput);
		} finally {
			fileOutput.close();
		}
	}
	
	public int saveToStream(OutputStream fileOutput) throws IOException {
		ObjectOutputStream oOutput = null;
		int totalItemCount = this.scores.size(), writtenItemCount = 0;
		try {
			oOutput = new ObjectOutputStream(fileOutput);
			oOutput.writeInt(this.scores.size());
			for (ScoreEntry e : this.scores) {
				oOutput.writeObject(e);
				++writtenItemCount;
			}
			oOutput.writeInt(writtenItemCount);
		} finally {
			try {
				oOutput.close();
			} catch (IOException e) {
				;
			}
		}
		if (totalItemCount == writtenItemCount) {
			return writtenItemCount;
		} else {
			throw new IllegalStateException("Mismatch in write counts.");
		}
	}

	public int size() {
		return this.scores.size();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int rank = 0;
		for (ScoreEntry e : this.scores) {
			result.append(String.format("%d\t%s%n", ++rank, e));
		}
		return result.toString();
	}

}
