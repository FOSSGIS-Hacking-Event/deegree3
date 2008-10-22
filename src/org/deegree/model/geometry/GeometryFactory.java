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

package org.deegree.model.geometry;

import java.util.List;

import org.deegree.model.crs.CRSFactory;
import org.deegree.model.crs.coordinatesystems.CoordinateSystem;
import org.deegree.model.geometry.composite.CompositeCurve;
import org.deegree.model.geometry.composite.CompositeSolid;
import org.deegree.model.geometry.composite.CompositeSurface;
import org.deegree.model.geometry.composite.GeometricComplex;
import org.deegree.model.geometry.multi.MultiCurve;
import org.deegree.model.geometry.multi.MultiGeometry;
import org.deegree.model.geometry.multi.MultiPoint;
import org.deegree.model.geometry.multi.MultiSolid;
import org.deegree.model.geometry.multi.MultiSurface;
import org.deegree.model.geometry.primitive.Curve;
import org.deegree.model.geometry.primitive.CurveSegment;
import org.deegree.model.geometry.primitive.Envelope;
import org.deegree.model.geometry.primitive.LineString;
import org.deegree.model.geometry.primitive.LinearRing;
import org.deegree.model.geometry.primitive.OrientableCurve;
import org.deegree.model.geometry.primitive.Point;
import org.deegree.model.geometry.primitive.Ring;
import org.deegree.model.geometry.primitive.Solid;
import org.deegree.model.geometry.primitive.Surface;
import org.deegree.model.geometry.primitive.SurfacePatch;
import org.deegree.model.geometry.primitive.curvesegments.Arc;
import org.deegree.model.geometry.primitive.curvesegments.ArcByBulge;
import org.deegree.model.geometry.primitive.curvesegments.ArcByCenterPoint;
import org.deegree.model.geometry.primitive.curvesegments.ArcString;
import org.deegree.model.geometry.primitive.curvesegments.ArcStringByBulge;
import org.deegree.model.geometry.primitive.curvesegments.BSpline;
import org.deegree.model.geometry.primitive.curvesegments.Bezier;
import org.deegree.model.geometry.primitive.curvesegments.Circle;
import org.deegree.model.geometry.primitive.curvesegments.CircleByCenterPoint;
import org.deegree.model.geometry.primitive.curvesegments.Clothoid;
import org.deegree.model.geometry.primitive.curvesegments.CubicSpline;
import org.deegree.model.geometry.primitive.curvesegments.Geodesic;
import org.deegree.model.geometry.primitive.curvesegments.GeodesicString;
import org.deegree.model.geometry.primitive.curvesegments.Knot;
import org.deegree.model.geometry.primitive.curvesegments.LineStringSegment;
import org.deegree.model.geometry.primitive.curvesegments.OffsetCurve;
import org.deegree.model.geometry.standard.curvesegments.AffinePlacement;
import org.deegree.model.gml.Angle;
import org.deegree.model.gml.Length;

/**
 * 
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author$
 * 
 * @version. $Revision$, $Date$
 */
public interface GeometryFactory {

    /**
     * 
     * @return name of a factory
     */
    public String getName();

    /**
     * sets the name of a factory
     * 
     * @param name
     *            name of a factory
     */
    public void setName( String name );

    /**
     * 
     * @return short description of a factory and provided geometry implementaion
     */
    public String getDescription();

    /**
     * sets a short description of a factory and provided geometry implementaion
     * 
     * @param description
     */
    public void setDescription( String description );

    /**
     * A concrete GeometryFactory possibly supports just a subset of concrete geometries defined by deegree's geometry
     * interfaces. At least simple geometries must be supported:
     * <ul>
     * <li>Point</li>
     * <li>Curve</li>
     * <li>Surface</li>
     * <li>MultiPoint</li>
     * <li>MultiCurve</li>
     * <li>MultiSurface</li>
     * </ul>
     * 
     * @return list of supported geometry classes
     */
    public List<Class<?>> getSupportedGeometries();

    /**
     * @see #getSupportedGeometries()
     * @param supportedGeometries
     *            list of supported geometry classes
     */
    public void setSupportedGeometries( List<Class<?>> supportedGeometries );

    /**
     * Each GeometryFactory should a least support creating Curves with linear interpolation. Possible values are:
     * <ul>
     * <li>linear</li>
     * <li>geodesic</li>
     * <li>circularArc3Points</li>
     * <li>circularArc2PointWithBulge</li>
     * <li>elliptical</li>
     * <li>conic</li>
     * <li>cubicSpline</li>
     * <li>polynomialSpline</li>
     * <li>rationalSpline</li>
     * </ul>
     * 
     * @return list of supported curve interpolations
     */
    public List<CurveSegment.Interpolation> getSupportedCurveInterpolations();

    /**
     * @see #getSupportedCurveInterpolations()
     * @param interpolations
     */
    public void setSupportedCurveInterpolations( List<CurveSegment.Interpolation> interpolations );

    /**
     * Each GeometryFactory should a least support creating Surfaces with none interpolation. Possible values are:
     * <ul>
     * <li>none</li>
     * <li>planar</li>
     * <li>spherical</li>
     * <li>elliptical</li>
     * <li>conic</li>
     * <li>tin</li>
     * <li>bilinear</li>
     * <li>biquadratic</li>
     * <li>bicubic</li>
     * <li>polynomialSpline</li>
     * <li>rationalSpline</li>
     * <li>triangulatedSpline</li>
     * </ul>
     * 
     * @return list of supported surface interpolations
     */
    public List<SurfacePatch.Interpolation> getSupportedSurfaceInterpolations();

    /**
     * @see #getSupportedSurfaceInterpolations()
     * @param interpolations
     */
    public void setSupportedSurfaceInterpolations( List<SurfacePatch.Interpolation> interpolations );

    /**
     * creates a georeferenced point
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param coordinates
     *            coordinate values
     * @param precision
     *            precision of the point coordinates in units of the used CRS. This value will be used for comparing two
     *            points to be equal or not.
     * @param crs
     *            points coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Point}
     */
    public Point createPoint( String id, double[] coordinates, double precision, CoordinateSystem crs );

    /**
     * creates a georeferenced point with a default precision (
     * {@link #createPoint(String, double[], double, CoordinateSystem)})
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param coordinates
     *            coordinate values
     * @param crs
     *            points coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Point}
     */
    public Point createPoint( String id, double[] coordinates, CoordinateSystem crs );

    /**
     * Creates a {@link LineString} geometry.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param crs
     *            coordinate reference system
     * @param points
     *            list of control points for the line string
     * @return created {@link LineString}
     */
    public LineString createLineString( String id, CoordinateSystem crs, List<Point> points );

    /**
     * Creates a {@link Curve} from a two dimensional array of coordinates. Each field of the first dimension represents
     * one point.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param coordinates
     *            array of curve coordinates
     * @param crs
     *            curves coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Curve}
     */
    public Curve createCurve( String id, Point[][] coordinates, CoordinateSystem crs );

    /**
     * Creates a segmented {@link Curve} from one or more {@link CurveSegment}s. The last {@link Point} of i'th segment
     * must equals first {@link Point} of i+1'th segment.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param segments
     *            segments a curve shall be created from
     * @param crs
     *            curves coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Curve}
     */
    public Curve createCurve( String id, CurveSegment[] segments, CoordinateSystem crs );

    /**
     * Creates a {@link LineStringSegment} curve segment.
     * 
     * @param points
     *            list of points to create the {@link LineStringSegment} from
     * @return created {@link CurveSegment}
     */
    public LineStringSegment createLineStringSegment( List<Point> points );

    /**
     * Creates an {@link Arc} curve segment.
     * 
     * @param p1
     *            first control point
     * @param p2
     *            second control point
     * @param p3
     *            third control point
     * 
     * @return created {@link Arc}
     */
    public Arc createArc( Point p1, Point p2, Point p3 );

    /**
     * Creates an {@link ArcByBulge} curve segment.
     * 
     * @param p1
     *            first control point
     * @param p2
     *            second control point
     * @param bulge
     *            height of the arc (multiplier for the normals)
     * @param normal
     *            normal vector, in 2D only one coordinate is necessary
     * @return created {@link ArcStringByBulge}
     */
    public ArcByBulge createArcByBulge( Point p1, Point p2, double bulge, Point normal );

    /**
     * Creates an {@link ArcByCenterPoint} curve segment.
     * 
     * @param midPoint
     * @param radius
     * @param startAngle
     * @param endAngle
     * @return created {@link ArcByCenterPoint}
     */
    public ArcByCenterPoint createArcByCenterPoint( Point midPoint, Length radius, Angle startAngle, Angle endAngle );

    /**
     * Creates an {@link ArcString} curve segment.
     * 
     * @param points
     *            list of control points, must contain <code>2 * k + 1</code> points
     * @return created {@link ArcString}
     */
    public ArcString createArcString( List<Point> points );

    /**
     * Creates an {@link ArcStringByBulge} curve segment.
     * <p>
     * This variant of the arc computes the mid points of the arcs instead of storing the coordinates directly. The
     * control point sequence consists of the start and end points of each arc plus the bulge.
     * 
     * @param points
     *            list of control points, must contain at least two points
     * @param bulges
     *            heights of the arcs (multipliers for the normals)
     * @param normals
     *            normal vectors
     * @return created {@link ArcStringByBulge}
     */
    public ArcStringByBulge createArcStringByBulge( List<Point> points, double[] bulges, List<Point> normals );

    /**
     * Creates a {@link Bezier} curve segment.
     * 
     * @param points
     *            list of control points
     * @param degree
     *            polynomial degree of the spline
     * @param knot1
     *            first of the two knots that define the spline basis functions
     * @param knot2
     *            second of the two knots that define the spline basis functions
     * @return created {@link Bezier}
     */
    public Bezier createBezier( List<Point> points, int degree, Knot knot1, Knot knot2 );

    /**
     * Creates a {@link BSpline} curve segment.
     * 
     * @param points
     *            list of control points
     * @param degree
     *            polynomial degree of the spline
     * @param knots
     *            sequence of distinct knots that define the spline basis functions
     * @param isPolynomial
     *            set to true if this is a polynomial spline, otherwise it's a rational spline
     * @return created {@link BSpline}
     */
    public BSpline createBSpline( List<Point> points, int degree, List<Knot> knots, boolean isPolynomial );

    /**
     * Creates a {@link Circle} curve segment.
     * 
     * @param p1
     *            first control point
     * @param p2
     *            second control point
     * @param p3
     *            third control point
     * 
     * @return created {@link Arc}
     */
    public Circle createCircle( Point p1, Point p2, Point p3 );

    /**
     * Creates an {@link CircleByCenterPoint} curve segment.
     * 
     * @param midPoint
     * @param radius
     * @param startAngle
     * @return created {@link CircleByCenterPoint}
     */
    public CircleByCenterPoint createCircleByCenterPoint( Point midPoint, Length radius, Angle startAngle );

    /**
     * Creates an {@link Clothoid} curve segment.
     * 
     * @param referenceLocation
     *            the affine mapping that places the curve defined by the Fresnel Integrals into the coordinate
     *            reference system of this object
     * @param scaleFactor
     *            the value for the constant in the Fresnel's integrals
     * @param startParameter
     *            the arc length distance from the inflection point that will be the start point for this curve segment
     * @param endParameter
     *            the arc length distance from the inflection point that will be the end point for this curve segment
     * @return created {@link Clothoid}
     */
    public Clothoid createClothoid( AffinePlacement referenceLocation, double scaleFactor, double startParameter,
                                    double endParameter );

    /**
     * Creates a {@link Clothoid} curve segment.
     * 
     * @param points
     *            control points, at least two
     * @param vectorAtStart
     *            the unit tangent vector at the start point of the spline
     * @param vectorAtEnd
     *            the unit tangent vector at the end point of the spline
     * @return created {@link Clothoid}
     */
    public CubicSpline createCubicSpline( List<Point> points, Point vectorAtStart, Point vectorAtEnd );

    /**
     * Creates a {@link Geodesic} curve segment.
     * 
     * @param p1
     *            first control point
     * @param p2
     *            second control point
     * @return created {@link Geodesic}
     */
    public Geodesic createGeodesic( Point p1, Point p2 );

    /**
     * Creates a {@link GeodesicString} curve segment.
     * 
     * @param points
     *            control points, at least two
     * @return created {@link GeodesicString}
     */
    public GeodesicString createGeodesicString( List<Point> points );

    /**
     * Creates an {@link OffsetCurve} curve segment.
     * 
     * @param baseCurve
     *            the base geometry
     * @param direction
     *            the direction of the offset
     * @param distance
     *            the distance from the base curve
     * @return created {@link GeodesicString}
     */
    public OffsetCurve createOffsetCurve( Curve baseCurve, Point direction, Length distance );

    /**
     * Creates a {@link Surface} from an array of {@link Curve}s forming the boundary of a {@link Surface}. The first
     * {@link Curve} forms the outer boundary the following {@link Curve}s represents holes. The passed interpolation
     * must be supported by the concrete GeometryFactory; {@link #getSupportedSurfaceInterpolations()}.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param boundary
     *            surfaces boundary
     * @param interpolation
     *            interpolation to be used
     * @param crs
     *            surfaces coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Surface}
     */
    public Surface createSurface( String id, List<Curve> boundary, SurfacePatch.Interpolation interpolation,
                                  CoordinateSystem crs );

    /**
     * creates a {@link Surface} from an array of {@link SurfacePatch}. The passed patches must touch in a topological
     * sense to form a valid {@link Surface}
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param patches
     *            patches to create a surface
     * @param crs
     *            surfaces coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Surface}
     */
    public Surface createSurface( String id, List<SurfacePatch> patches, CoordinateSystem crs );

    /**
     * Creates a {@link SurfacePatch} from an array of {@link Curve}s forming the boundary of a {@link SurfacePatch}.
     * The first {@link Curve} forms the outer boundary the following {@link Curve}s represents holes. The passed
     * interpolation must be supported by the concrete GeometryFactory; {@link #getSupportedSurfaceInterpolations()}.
     * The passed type defines the {@link CurveSegment} class to be used (possibly different kinds of
     * {@link SurfacePatch} implementations are available; see ISO 19107 for details)
     * 
     * @param boundary
     *            boundary of the {@link SurfacePatch}
     * @param type
     *            concrete type of the {@link SurfacePatch}
     * @param interpolation
     *            interporlation to be used
     * @return created {@link SurfacePatch}
     */
    public SurfacePatch createSurfacePatch( List<Curve> boundary, Class<?> type,
                                            SurfacePatch.Interpolation interpolation );

    /**
     * Creates a {@link SurfacePatch} from an array of {@link Curve}s forming the boundary of a {@link SurfacePatch}.
     * The first {@link Curve} forms the outer boundary the following {@link Curve}s represents holes. Default
     * interpolation and type will be used.
     * 
     * @param boundary
     *            boundary of the {@link SurfacePatch}
     * @return created {@link SurfacePatch}
     */
    public SurfacePatch createSurfacePatch( List<Curve> boundary );

    /**
     * Creates a Solid from a from a set of {@link Surface}s forming its outer boundary and 0..n sets of {@link Surface}
     * s forming its inner boundaries. The support for {@link Solid}s is optional. An implementation that does not
     * support is shall throw an {@link UnsupportedOperationException} if this method will be invoked.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param outerboundary
     * @param innerboundaries
     * @param crs
     *            solids coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Solid}
     */
    public Solid createSolid( String id, Surface[] outerboundary, Surface[][] innerboundaries, CoordinateSystem crs );

    /**
     * Creates an untyped multi geometry from a list of {@link Geometry}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param geometries
     * @return created {@link MultiGeometry}
     */
    public MultiGeometry<Geometry> createMultiGeometry( String id, List<Geometry> geometries );

    /**
     * Creates a {@link MultiPoint} from a list of passed {@link Point}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param points
     * @return created {@link MultiPoint}
     */
    public MultiPoint createMultiPoint( String id, List<Point> points );

    /**
     * Creates a {@link MultiCurve} from a list of passed {@link Curve}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param curves
     * @return created {@link MultiCurve}
     */
    public MultiCurve createMultiCurve( String id, List<Curve> curves );

    /**
     * Creates a {@link MultiSurface} from a list of passed {@link Surface}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param surfaces
     * @return created {@link MultiSurface}
     */
    public MultiSurface createMultiSurface( String id, List<Surface> surfaces );

    /**
     * Creates a {@link MultiSolid} from a list of passed {@link Solid}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param solids
     * @return created {@link MultiSolid}
     */
    public MultiSolid createMultiSolid( String id, List<Solid> solids );

    /**
     * Creates a {@link CompositeCurve} from a list of passed {@link Curve}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param curves
     * @return created {@link CompositeCurve}
     */
    public CompositeCurve createCompositeCurve( String id, List<Curve> curves );

    /**
     * Creates a {@link CompositeSurface} from a list of passed {@link Surface}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param surfaces
     * @return created {@link CompositeSurface}
     */
    public CompositeSurface createCompositeSurface( String id, List<Surface> surfaces );

    /**
     * Creates a {@link CompositeSolid} from a list of passed {@link Solid}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param solids
     * @return created {@link CompositeSolid}
     */
    public CompositeSolid createCompositeSolid( String id, List<Solid> solids );

    /**
     * Creates a general {@link GeometricComplex} from a list of geometries.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param geometries
     * @return created {@link GeometricComplex}
     */
    public GeometricComplex createGeometricComplex( String id, List<Geometry> geometries );

    /**
     * creates an {@link Envelope} with a defined precision
     * 
     * @param min
     *            minimum corner coordinates
     * @param max
     *            miximum corner coordinates
     * @param precision
     *            precision of the corner coordinates in units of the used CRS. This value will be used for comparing
     *            two points to be equal or not.
     * @param crs
     *            evenlopes coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Envelope}
     */
    public Envelope createEnvelope( double[] min, double[] max, double precision, CoordinateSystem crs );

    /**
     * creates an {@link Envelope} with default precision
     * 
     * @param min
     *            minimum corner coordinates
     * @param max
     *            miximum corner coordinates
     * @param crs
     *            evenlopes coordinate reference system. If a point does not have a CRS or it is not known
     *            {@link CRSFactory#createDummyCRS(String)} shall be used instead of <code>null</code>
     * @return created {@link Envelope}
     */
    public Envelope createEnvelope( double[] min, double[] max, CoordinateSystem crs );

    /**
     * creates an envelope from a SurfacePatch representing a envelope by being constructed by five points: minx,miny
     * minx,maxy maxx,maxy maxx,miny minx,miny
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param patch
     * @return envelope created from a SurfacePatch
     */
    public Envelope createEnvelope( String id, SurfacePatch patch );

    /**
     * Creates a {@link Ring} from a list of passed {@link Curve}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param crs
     *            coordinate reference system
     * @param members
     *            the <code>Curve</code>s that compose the <code>Ring</code>
     * @return created {@link Ring}
     */
    public Ring createRing( String id, CoordinateSystem crs, List<Curve> members );

    /**
     * Creates a simple {@link LinearRing} from a list of passed {@link Point}s.
     * 
     * @param id
     *            identifier of the new geometry instance
     * @param crs
     *            coordinate reference system
     * @param points
     *            the control points
     * @return created {@link Ring}
     */
    public LinearRing createLinearRing( String id, CoordinateSystem crs, List<Point> points );

    /**
     * Creates an {@link OrientableCurve}.
     * 
     * @param id
     *            identifier of the created geometry object
     * @param crs
     *            coordinate reference system
     * @param baseCurve
     *            base curve
     * @param isReversed
     *            set to true, if the order of the base curve shall be reversed
     * @return createsd {@link OrientableCurve}
     */
    public OrientableCurve createOrientableCurve( String id, CoordinateSystem crs, Curve baseCurve, boolean isReversed );
}
