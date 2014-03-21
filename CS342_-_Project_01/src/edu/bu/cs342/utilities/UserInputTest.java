package edu.bu.cs342.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.CharBuffer;

import org.junit.Before;
import org.junit.Test;

public class UserInputTest {

	private class StringSource implements Readable, Closeable {
		private StringBuilder buffer;
		private boolean opened;
		public StringSource(StringBuilder sb) {
			this.buffer = sb;
			this.opened = true;
		}
		@Override
		public int read(CharBuffer arg0) throws IOException {
			if (this.opened) {
				String inBuffer = this.buffer.toString();
				this.buffer.setLength(0);
				arg0.put(inBuffer);
				return inBuffer.length();
			} else {
				throw new IOException("StringSource not opened.");
			}
		}
		@Override
		public void close() throws IOException {
			this.buffer = null;
			this.opened = false;
		}
		
	}
	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;
	private StringSource input;
	private ByteArrayOutputStream byteStream;
	private PrintStream output;
	private UserInput reader;
	
	private StringBuilder builder;

	@Before
	public void setUp() throws Exception {
		this.builder = new StringBuilder();
		this.input = new UserInputTest.StringSource(this.builder);
		this.byteStream = new ByteArrayOutputStream();
		this.output = new PrintStream(this.byteStream);
		this.reader = new UserInput(this.input, this.output);
	}

	@Test
	public void testPrompt() {
		this.builder.append("1\n");
		this.reader.readInteger("");
		this.builder.append("1\n");
		this.reader.readInteger("prompt");
		String output = this.byteStream.toString();
		assertEquals("prompt:\t", output);
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
		assert output.contains(UserInput.STRING_LENGTH_ERROR);
	}
	@Test
	public void testReadBoolean() {
		this.builder.append("Y\n");
		assertEquals(true, this.reader.readBoolean("prompt"));
		this.builder.append("N\n");
		assertFalse(this.reader.readBoolean("prompt"));
		this.builder.append("Unknown\n");
		assertEquals(true, this.reader.readBoolean("prompt", "Y", "N", true));
		this.builder.append("Unknown\n");
		assertFalse(this.reader.readBoolean("prompt", "Y", "N", false));
	}
	
	@Test
	public void testReadBooleanError() {
		this.builder.append("Yes\nY\n");
		assertEquals(true, this.reader.readBoolean("prompt"));
		String output = this.byteStream.toString();
		assert output.lastIndexOf("prompt") > output.indexOf("prompt");
	}
	@Test
	public void testReadDouble() {
		this.builder.append("7.3\n");
		assertEquals(7.3d, this.reader.readDouble("prompt"), UserInputTest.ALLOWABLE_FLOAT_VARIANCE);
	}

	@Test
	public void testReadDoubleInteger() {
		this.builder.append("7\n");
		assertEquals(7.0d, this.reader.readDouble("prompt"), UserInputTest.ALLOWABLE_FLOAT_VARIANCE);
	}
	@Test
	public void testReadDoubleIntegerError() {
		this.builder.append("7.0\n7.3\n");
		assertEquals(7.3d, this.reader.readDouble("prompt", true), UserInputTest.ALLOWABLE_FLOAT_VARIANCE);
		String output = this.byteStream.toString();
		assert output.contains(UserInput.DECIMAL_ERROR);
	}
	@Test
	public void testReadDoubleParseError() {
		this.builder.append("q\n7.3\n");
		assertEquals(7.3d, this.reader.readDouble("prompt"), UserInputTest.ALLOWABLE_FLOAT_VARIANCE);
		String output = this.byteStream.toString();
		assert output.contains(UserInput.NUMBER_ERROR);
	}
	@Test
	public void testReadInteger() {
		this.builder.append("7\n");
		assertEquals(7, this.reader.readInteger("prompt"));
	}
	@Test
	public void testReadIntegerError() {
		this.builder.append("q\n7.6\n7\n");
		assertEquals(7, this.reader.readInteger("prompt"));
		String output = this.byteStream.toString();
		assert output.contains(UserInput.INTEGER_ERROR);
		assert output.indexOf(UserInput.INTEGER_ERROR) < output.lastIndexOf(UserInput.INTEGER_ERROR);
	}

	@Test
	public void testReadIntegerMinimum() {
		this.builder.append("3\n7\n");
		assertEquals(7, this.reader.readInteger("prompt", 7));
		String output = this.byteStream.toString();
		assert output.contains(String.format(UserInput.MIN_VALUE_ERROR, 7));
	}

	@Test
	public void testReadLine() {
		String testValue = "multiple tokens";
		this.builder.append(testValue);
		this.builder.append("\n");
		assertEquals(testValue,this.reader.readLine("prompt"));
		this.builder.append("\n");
		this.builder.append(testValue);
		this.builder.append("\n");
		assertEquals(testValue,this.reader.readLine("prompt"));
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
}
