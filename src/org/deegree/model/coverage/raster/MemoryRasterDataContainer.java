//$HeadURL$
/*----------------    FILE HEADER  ------------------------------------------
 This file is part of deegree.
 Copyright (C) 2001-2007 by:
 Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 Contact:

 Andreas Poth
 lat/lon GmbH
 Aennchenstr. 19
 53177 Bonn
 Germany
 E-Mail: poth@lat-lon.de

 Prof. Dr. Klaus Greve
 Department of Geography
 University of Bonn
 Meckenheimer Allee 166
 53115 Bonn
 Germany
 E-Mail: greve@giub.uni-bonn.de
 ---------------------------------------------------------------------------*/
package org.deegree.model.coverage.raster;

import org.deegree.model.coverage.raster.data.RasterData;

/**
 * This class implements a RasterDataContainer that keeps RasterData in memory.
 * 
 * @author <a href="mailto:tonnhofer@lat-lon.de">Oliver Tonnhofer</a>
 * @author last edited by: $Author$
 *
 * @version $Revision$, $Date$
 * 
 */
public class MemoryRasterDataContainer implements RasterDataContainer {

    private RasterData raster;

    /**
     * Reads RasterData from RasterReader and wraps it in a RasterDataContainer. RasterData stays in memory.
     * 
     * @param reader
     *            RasterReader for RasterData
     */
    public MemoryRasterDataContainer( RasterReader reader ) {
        this.raster = reader.read();
    }

    /**
     * Wraps RasterData in a RasterDataContainer. RasterData stays in memory.
     * 
     * @param raster
     *            RasterData to wrap
     */
    public MemoryRasterDataContainer( RasterData raster ) {
        this.raster = raster;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.deegree.model.raster.RasterDataContainer#getColumns()
     */
    public int getColumns() {
        return raster.getWidth();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.deegree.model.raster.RasterDataContainer#getRows()
     */
    public int getRows() {
        return raster.getHeight();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.deegree.model.raster.RasterDataContainer#getRasterData()
     */
    public RasterData getRasterData() {
        return raster;
    }

}
