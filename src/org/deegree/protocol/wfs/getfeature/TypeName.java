//$HeadURL: svn+ssh://mschneider@svn.wald.intevation.org/deegree/base/trunk/resources/eclipse/files_template.xml $
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

package org.deegree.protocol.wfs.getfeature;

import javax.xml.namespace.QName;

/**
 * A feature type name with an optional alias, as it may be used in WFS 1.1.0 or 2.0.0 queries.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: schneider $
 * 
 * @version $Revision: $, $Date: $
 */
public class TypeName {

    private final QName ftName;

    private final String alias;

    /**
     * Creates a new {@link TypeName} with optional alias.
     * 
     * @param ftName
     *            name of the feature, must not be null
     * @param alias
     *            alias for the feature type, may be null
     */
    public TypeName( QName ftName, String alias ) {
        if ( ftName == null ) {
            throw new IllegalArgumentException();
        }
        this.ftName = ftName;
        this.alias = alias;
    }

    /**
     * Returns the feature type name.
     * 
     * @return the feature type name, never null
     */
    public QName getFeatureTypeName() {
        return ftName;
    }

    /**
     * Returns the alias for the feature type.
     * 
     * @return the alias for the feature type, or null if it has none
     */
    public String getAlias() {
        return alias;
    }
}
