package jbasic.app.ide;

import ibmpc.devices.display.IbmPcDisplay;
import ibmpc.devices.display.IbmPcDisplayFrame;
import ibmpc.devices.storage.IbmPcStorageSystem;

import java.awt.Font;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import jbasic.common.exceptions.JBasicException;
import jbasic.common.program.JBasicCompiledCode;
import jbasic.common.program.JBasicCompiler;
import jbasic.common.program.JBasicRuntime;
import jbasic.common.program.JBasicSourceCode;
import jbasic.common.program.OpCode;
import jbasic.gwbasic.GwBasicEnvironment;
import jbasic.gwbasic.program.GwBasicCompiledCode;
import jbasic.gwbasic.program.GwBasicCompiler;
import jbasic.gwbasic.program.GwBasicProgram;
import jbasic.gwbasic.program.GwBasicRuntime;
import jbasic.gwbasic.program.commands.GwBasicCommand;
import jbasic.gwbasic.storage.GwBasicStorageDevice;

/**
 * JBasic Source File Editor
 * @author lawrence.daniels@gmail.com
 */
@SuppressWarnings("serial")
public class JBasicEditor extends JTextPane {
	private final JBasicSyntaxHightlighter syntaxHightlighter;
	private final JBasicIdeContext context;
	private final JBasicCompiler compiler;
	private final JBasicRuntime runtime;
	private final Collection<OpCode> opCodes;
	private final GwBasicEnvironment environment;
	private final JBasicSourceCode program;
	private final GwBasicStorageDevice storageDevice;
	private final IbmPcDisplayFrame programFrame;
	
	/**
	 * Default constructor
	 * @param frame the given {@link JBasicIdeContext context}
	 */
	public JBasicEditor( JBasicIdeContext context ) {
		this.context 			= context;
		this.opCodes 			= new LinkedList<OpCode>();
		this.syntaxHightlighter = new JBasicSyntaxHightlighter( this );	
		this.programFrame 		= new IbmPcDisplayFrame( "JBasic Runtime" );
		this.environment 		= new GwBasicEnvironment( programFrame );
		this.compiler			= GwBasicCompiler.getInstance();
		this.runtime			= GwBasicRuntime.getInstance();
		this.program 			= new GwBasicProgram( environment );
		this.storageDevice 		= environment.getStorageSystem();
		
		// setup the default font
		super.setFont( new Font( "Courier", Font.TRUETYPE_FONT, 14 ) );		
	}
	
	/**
	 * Loads the given source file into this editor
	 * @param sourceFile the given source file
	 */
	public void loadFile( File sourceFile ) {
		try {
			// load the source file
			super.read( new FileReader( sourceFile ), null );
			
			// update the highlighter
			//syntaxHightlighter.updateAll();
			
			// compile the program
			final GwBasicCompiledCode compiledCode = (GwBasicCompiledCode)compile( sourceFile );
			
			// get the opcodes
			final GwBasicCommand[] theOpCodes = compiledCode.getCommands();
			
			// clear the current opCodes
			opCodes.clear();
			opCodes.addAll( Arrays.asList( theOpCodes ) );
			
			// update explorer
			JBasicExplorer explorer = context.getExplorer();
			explorer.updateTree( theOpCodes );
		} 
		catch( IOException e ) {
			JOptionPane.showMessageDialog( this, e.getMessage() );
		}
	}	
	
	/**
	 * Run the program
	 */
	public void run() {
		try {
			// initialize the display
			if( !programFrame.isVisible() ) {
				IbmPcDisplay display = environment.getDisplay();
				display.init( programFrame );	
			}
			
			// run the program
			runtime.run( program );
		} 
		catch (JBasicException e) {
			JOptionPane.showMessageDialog( this, e.getMessage() );
		}
	}
	
	/**
	 * Compile the source file into opCodes.
	 * @param sourceFile the given {@link File source file}
	 * @return the {@link OpCode opCodes}
	 */
	private JBasicCompiledCode compile( final File sourceFile ) {
		try {			
			// load the program
			storageDevice.load( program, sourceFile );
	
			// compile into opCodes
			return compiler.compile( program );		
		} 
		catch( JBasicException e ) {
			return new GwBasicCompiledCode( environment );
		}
	}
	
}
