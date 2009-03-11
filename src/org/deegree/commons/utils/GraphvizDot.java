//$HeadURL$
/*----------------    FILE HEADER  ------------------------------------------
 This file is part of deegree.
 Copyright (C) 2001-2009 by:
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

package org.deegree.commons.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * The <code>GraphvizDot</code> class provides a few methods to create a graphviz 'dot' file. For more information on
 * dot files please see http://www.graphviz.org
 * 
 * @author <a href="mailto:bezema@lat-lon.de">Rutger Bezema</a>
 * @author last edited by: $Author$
 * @version $Revision$, $Date$
 * 
 */
public class GraphvizDot {

    /**
     * Writes the starting directed graph definition.
     * 
     * @param writer
     * @throws IOException
     */
    public static void startDiGraph( Writer writer )
                            throws IOException {
        writer.write( "/* Created with deegree GraphvizDot writer.*/\n" );
        writer.write( "/* Create an svg image with following command: */\n" );
        writer.write( "/* $dot dot -Tsvg -o image_out.svg  output.dot */\n" );
        writer.write( "digraph G {\n" );
        writer.write( "style=\"filled\";\n" );
    }

    /**
     * Writes the last closing bracket.
     * 
     * @param writer
     * @throws IOException
     */
    public static void endGraph( Writer writer )
                            throws IOException {
        writer.write( "}\n" );
    }

    /**
     * Writes a vertex.
     * 
     * @param id
     *            of the vertex
     * @param attr_List
     *            of attributes to write to the vertex definition
     * 
     * @param writer
     * @throws IOException
     */
    public static void writeVertex( String id, List<String> attr_List, Writer writer )
                            throws IOException {
        writer.write( "\"" );
        writer.write( id );
        writer.write( "\"" );
        writeAttributeList( attr_List, writer );
        writer.write( ";\n" );
    }

    /**
     * Writes a vertex.
     * 
     * @param from
     *            this vertex
     * @param to
     *            this vertex
     * 
     * @param attr_List
     *            of attributes to write to the edge definition
     * 
     * @param writer
     * @throws IOException
     */
    public static void writeEdge( String from, String to, List<String> attr_List, Writer writer )
                            throws IOException {
        writer.write( "\"" );
        writer.write( from );
        writer.write( "\"->\"" );
        writer.write( to );
        writer.write( "\"" );
        writeAttributeList( attr_List, writer );
        writer.write( ";\n" );
    }

    private static void writeAttributeList( List<String> attr_List, Writer writer )
                            throws IOException {
        if ( attr_List != null && !attr_List.isEmpty() ) {
            writer.write( " [" );
            for ( int i = 0; i < attr_List.size(); ++i ) {
                String a = attr_List.get( i );
                if ( a != null ) {
                    writer.write( a );
                }
                if ( i + 1 < attr_List.size() ) {
                    writer.write( "," );
                }
            }
            writer.write( "]" );
        }
    }

    /**
     * @param label
     *            to set
     * @return a label statement in the graphviz language
     */
    public static String getLabelDef( String label ) {
        return "label=\"" + label + "\"";
    }

    /**
     * http://www.graphviz.org/doc/info/colors.html
     * 
     * @param color
     *            to set
     * @return a fill color statement in the graphviz language
     */
    public static String getFillColorDef( String color ) {
        return "style=\"filled\",fillcolor=\"" + color + "\"";
    }

    /**
     * http://www.graphviz.org/doc/info/shapes.html
     * 
     * @param shape
     *            to set
     * @return a fill color statement in the graphviz language
     */
    public static String getShapeDef( String shape ) {
        return "shape=\"" + shape + "\"";
    }
}
