package org.ldaniels528.javapc.ibmpc.devices.display.modes.ega;

import org.ldaniels528.javapc.ibmpc.devices.display.IbmPcColors;
import org.ldaniels528.javapc.ibmpc.devices.display.IbmPcDisplayContext;
import org.ldaniels528.javapc.ibmpc.devices.display.modes.AbstractPackedPixelGraphicsMode;

import java.awt.Color;

/**
 * Represents the EGA 640x350 4-Color (Packed Pixel) Graphics Mode
 * @author lawrence.daniels@gmail.com
 */
public class EGAGraphicsMode640x350Mono extends AbstractPackedPixelGraphicsMode {
	private static final Color[] COLOR_MAP		= {
		IbmPcColors.COLORS_16[0], IbmPcColors.COLORS_16[3],
		IbmPcColors.COLORS_16[5], IbmPcColors.COLORS_16[15]
	};	
	private static final int COLUMNS			= 80;
	private static final int ROWS				= 25;
	private static final int RES_WIDTH 			= 640;
	private static final int RES_HEIGHT			= 350;
	private static final int COLORS				= 4;
	private static final int MEMORY_SEGMENT		= 0xA000;
	private static final int MEMORY_SIZE		= 65536;
	private static final int PIXELS_PER_BYTE	= 4;
	private final int[] pixeldata;

	/**
	 * Default constructor
	 */
	public EGAGraphicsMode640x350Mono() {
		super( 0x0F, MEMORY_SEGMENT, COLUMNS, ROWS, RES_WIDTH, RES_HEIGHT, 8, 14, 
				PIXELS_PER_BYTE, MEMORY_SIZE, COLORS,COLOR_MAP );
		this.pixeldata = new int[PIXELS_PER_BYTE];
	}

	/* (non-Javadoc)
	 * @see org.ldaniels528.javapc.ibmpc.display.modes.AbstractDisplayMode#readPixel(org.ldaniels528.javapc.ibmpc.display.IbmPcDisplayContext, int, int)
	 */
	public int readPixel( IbmPcDisplayContext dc, int x, int y ) {
		// get the segment portion of the address
		final int offset	 = getPixelOffset( x, y );
		
		// get the pixel data at the given segment and offset
		final int pixeldata = dc.memory.getByte( memorySegment, offset );
		
		// isolate the single pixel's color
		final int pixelIndex = ( x % PIXELS_PER_BYTE );
		final int multiplier = 6 - ( pixelIndex << 1 );
		final int colorIndex = ( pixeldata >> multiplier ) & 0x03;
		
		// return the color
		return colorIndex;
	}

	/* (non-Javadoc)
	 * @see org.ldaniels528.javapc.ibmpc.display.modes.AbstractDisplayMode#writePixel(org.ldaniels528.javapc.ibmpc.display.IbmPcDisplayContext, int, int, int)
	 */
	public void drawPixel( IbmPcDisplayContext dc, int x, int y, int colorIndex ) {
		// get the segment portion of the address
		final int offset	 = getPixelOffset( x, y );
		
		// get the pixel data at the given segment and offset
		final int pixelIndex = ( x % PIXELS_PER_BYTE );
		final int multiplier = 6 - ( pixelIndex << 1 );
		
		// write the pixel to memory
		dc.memory.setBits( memorySegment, offset, ( colorIndex & 0x03 ) << multiplier );		
	}
	
	/**
	 * Decodes the packed pixels and draws them to the screen
	 * <pre>
	 * Bits	Description
	 * ----	-----------
     * 0-1	fourth pixel in byte
     * 2-3	third pixel in byte
     * 4-5	second pixel in byte
     * 6-7	first pixel in byte
     * </pre>
	 * @param packedPixels the given packed pixels 
	 */
	protected int[] decodePackedPixels( int packedPixels ) {
		pixeldata[0] = ( packedPixels & 0xC0 ) >> 6;	// 11000000
		pixeldata[1] = ( packedPixels & 0x30 ) >> 4;	// 00110000
		pixeldata[2] = ( packedPixels & 0x0C ) >> 2;	// 00001100
		pixeldata[3] = ( packedPixels & 0x03 );			// 00000011
		return pixeldata;
	}
	
}