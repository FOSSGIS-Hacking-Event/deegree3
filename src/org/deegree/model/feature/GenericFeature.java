//$HeadURL$
/*----------------    FILE HEADER  ------------------------------------------

 This file is part of deegree.
 Copyright (C) 2001-2008 by:
 EXSE, Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 Contact:

 Andreas Poth  
 lat/lon GmbH 
 Aennchenstr. 19
 53115 Bonn
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
package org.deegree.model.feature;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.deegree.model.feature.types.FeatureType;
import org.deegree.model.feature.types.GenericFeatureType;
import org.deegree.model.feature.types.PropertyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows the representation of arbitrary {@link Feature}s.
 * <p>
 * Please note that it is more efficient to use the {@link GenericSimpleFeature} class if the feature to be represented
 * does not contain multiple properties or nested features ("complex properties").
 * </p>
 * 
 * @see GenericSimpleFeature
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </a>
 * @author last edited by: $Author:$
 * 
 * @version $Revision:$, $Date:$
 */
public class GenericFeature extends AbstractFeature {

    private static final Logger LOG = LoggerFactory.getLogger( GenericFeature.class );

    private String fid;

    private GenericFeatureType ft;

    private List<Property<?>> props;

    public GenericFeature( GenericFeatureType ft, String fid, List<Property<?>> props ) {
        this.ft = ft;
        this.fid = fid;
        this.props = new ArrayList<Property<?>>( props );
    }

    @Override
    public String getId() {
        return fid;
    }

    @Override
    public void setId( String fid ) {
        this.fid = fid;
    }

    @Override
    public QName getName() {
        return ft.getName();
    }

    @Override
    public FeatureType getType() {
        return ft;
    }

    @Override
    public Property<?>[] getProperties() {
        return props.toArray( new Property<?>[props.size()] );
    }

    @Override
    public void setProperties( List<Property<?>> props )
                            throws IllegalArgumentException {
        this.props = new ArrayList<Property<?>>( props );
    }

    @Override
    public void setPropertyValue( QName propName, int occurrence, Object value ) {
        LOG.debug( "Setting property value for " + occurrence + ". " + propName + " property" );
        int num = 0;
        for ( int i = 0; i < props.size(); i++ ) {
            Property<?> prop = props.get( i );
            if ( prop.getName().equals( propName ) ) {
                if ( num++ == occurrence ) {
                    PropertyType pt = prop.getType();
                    props.set( i, new GenericProperty<Object>( pt, value ) );
                    LOG.debug( "Yep." );
                    break;
                }
            }
        }
    }

    @Override
    public Property<?>[] getProperties( QName propName ) {
        List<Property<?>> namedProps = new ArrayList<Property<?>>( props.size() );
        for ( Property<?> property : props ) {
            if ( propName.equals( property.getName() ) ) {
                namedProps.add( property );
            }
        }
        return namedProps.toArray( new Property<?>[namedProps.size()] );
    }

    @Override
    public Property<?> getProperty( QName propName ) {
        Property<?> prop = null;
        for ( Property<?> property : props ) {
            if ( propName.equals( property.getName() ) ) {
                if ( prop != null ) {
                    String msg = "Feature has more than one property with name '" + propName + "'.";
                    throw new IllegalArgumentException( msg );
                }
                prop = property;
            }
        }
        return prop;
    }

    @Override
    public Object getPropertyValue( QName propName ) {
        Object value = null;
        for ( Property<?> property : props ) {
            if ( propName.equals( property.getName() ) ) {
                if ( value != null ) {
                    String msg = "Feature has more than one property with name '" + propName + "'.";
                    throw new IllegalArgumentException( msg );
                }
                value = property.getValue();
            }
        }
        return value;
    }

    @Override
    public Object[] getPropertyValues( QName propName ) {
        List<Object> propValues = new ArrayList<Object>( props.size() );
        for ( Property<?> property : props ) {
            if ( propName.equals( property.getName() ) ) {
                propValues.add( property.getValue() );
            }
        }
        return propValues.toArray( new Object[propValues.size()] );
    }
}
