package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.Scanner;
import java.io.*;
import java.nio.CharBuffer;

import org.junit.Before;
import org.junit.Test;

public class InputReaderTest {

	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;
	private StringSource input;
	private ByteArrayOutputStream byteStream;
	private PrintStream output;
	private Scanner scanner;
	private InputReader reader;
	private StringBuilder builder;
	
	private class StringSource implements Readable {
		private StringBuilder buffer;
		public StringSource(StringBuilder sb) {
			this.buffer = sb;
		}
		@Override
		public int read(CharBuffer arg0) throws IOException {
			String inBuffer = this.buffer.toString();
			this.buffer.setLength(0);
			arg0.put(inBuffer);
			return inBuffer.length();
		}
		
	}

	@Before
	public void setUp() throws Exception {
		this.builder = new StringBuilder();
		this.input = new InputReaderTest.StringSource(this.builder);
		this.scanner = new Scanner(this.input);
		this.byteStream = new ByteArrayOutputStream();
		this.output = new PrintStream(this.byteStream);
		this.reader = new InputReader(this.scanner, this.output);
	}

	@Test
	public void testReadInteger() {
		this.builder.append("7\n");
		assertEquals(7, this.reader.readInteger("prompt"));
	}
	@Test
	public void testReadIntegerMinimum() {
		this.builder.append("3\n7\n");
		assertEquals(7, this.reader.readInteger("prompt", 7));
		String output = this.byteStream.toString();
		assert output.contains(String.format(InputReader.MIN_VALUE_ERROR, 7));
	}
	@Test
	public void testReadIntegerError() {
		this.builder.append("q\n7.6\n7\n");
		assertEquals(7, this.reader.readInteger("prompt"));
		String output = this.byteStream.toString();
		assert output.contains(InputReader.INTEGER_ERROR);
		assert output.indexOf(InputReader.INTEGER_ERROR) < output.lastIndexOf(InputReader.INTEGER_ERROR);
	}
	
	@Test
	public void testReadDouble() {
		this.builder.append("7.3\n");
		assertEquals(7.3d, this.reader.readDouble("prompt"), InputReaderTest.ALLOWABLE_FLOAT_VARIANCE);
	}
	@Test
	public void testReadDoubleInteger() {
		this.builder.append("7\n");
		assertEquals(7.0d, this.reader.readDouble("prompt"), InputReaderTest.ALLOWABLE_FLOAT_VARIANCE);
	}
	@Test
	public void testReadDoubleParseError() {
		this.builder.append("q\n7.3\n");
		assertEquals(7.3d, this.reader.readDouble("prompt"), InputReaderTest.ALLOWABLE_FLOAT_VARIANCE);
		String output = this.byteStream.toString();
		assert output.contains(InputReader.NUMBER_ERROR);
	}
	@Test
	public void testReadDoubleIntegerError() {
		this.builder.append("7.0\n7.3\n");
		assertEquals(7.3d, this.reader.readDouble("prompt", true), InputReaderTest.ALLOWABLE_FLOAT_VARIANCE);
		String output = this.byteStream.toString();
		assert output.contains(InputReader.DECIMAL_ERROR);
	}

	@Test
	public void testReadString() {
		String testValue = "Testing";
		this.builder.append(testValue);
		this.builder.append("\n");
		assertEquals(testValue, this.reader.readWord("prompt"));
	}
	@Test
	public void testReadStringReplace() {
		this.builder.append("aBcDeFgH\n");
		assertEquals("aBcDefGh", this.reader.readWord("prompt", "FgH", "fGh"));
	}
	@Test
	public void testReadStringByCharacter() {
		this.builder.append("abc\n\n");
		assertEquals("abc", this.reader.readWord("prompt", "", "", true, false));
	}
	@Test
	public void testReadAlphaWord() {
		this.builder.append("M1k3\n");
		assertEquals("Mk", this.reader.readAlphaWord("prompt"));
	}
	@Test
	public void testReadAlphaWordError() {
		this.builder.append("12345\nName\n");
		assertEquals("Name", this.reader.readAlphaWord("prompt"));
		String output = this.byteStream.toString();
		assert output.contains(InputReader.STRING_LENGTH_ERROR);
	}

	@Test
	public void testReadByCharacter() {
		this.builder.append("a\nbc\ndef\n\n");
		assertEquals("abcdef", this.reader.readByCharacter("prompt"));
	}
	@Test
	public void testReadByCharacterError() {
		this.builder.append("a\nbc\ndef\n\n");
		assertEquals("a", this.reader.readByCharacter("prompt", true));
		String output = this.byteStream.toString();
		assert output.contains(InputReader.CHAR_LENGTH_ERROR);
	}
}
