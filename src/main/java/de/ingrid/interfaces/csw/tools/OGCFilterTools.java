/**
 * 
 */
package de.ingrid.interfaces.csw.tools;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ingrid.utils.xml.Csw202NamespaceContext;
import de.ingrid.utils.xpath.XPathUtils;

/**
 * @author joachim
 * 
 */
public class OGCFilterTools {

    /** Tool for evaluating xpath **/
    private static XPathUtils xpath = new XPathUtils(new Csw202NamespaceContext());

    /**
     * Add a constraint with method PropertyIsEqual to the Query. If an
     * ogc:Filter/ogc:And construct already exists, add the constraint there.
     * 
     * @param queryNode
     * @param key
     * @param value
     */
    public static void addPropertyIsEqual(Node queryNode, String key, String value) {

        Document doc = queryNode.getOwnerDocument();

        if (!xpath.nodeExists(queryNode, "csw:Constraint")) {
            queryNode.appendChild(doc.createElementNS("http://www.opengis.net/cat/csw/2.0.2", "Constraint"));
        }

        if (xpath.nodeExists(queryNode, "csw:Constraint") && !xpath.nodeExists(queryNode, "csw:Constraint/ogc:Filter")) {
            Node constraintNode = xpath.getNode(queryNode, "csw:Constraint");
            constraintNode.appendChild(doc.createElementNS("http://www.opengis.net/ogc", "Filter"));
        }

        if (xpath.nodeExists(queryNode, "csw:Constraint/ogc:Filter")) {
            Node dstNode;
            if (xpath.nodeExists(queryNode, "csw:Constraint/ogc:Filter/ogc:And")) {
                dstNode = xpath.getNode(queryNode, "csw:Constraint/ogc:Filter/ogc:And");
            } else {
                Node filter = xpath.getNode(queryNode, "csw:Constraint/ogc:Filter");
                NodeList children = filter.getChildNodes();
                if (children.getLength() > 0) {
                    dstNode = filter.appendChild(doc.createElementNS("http://www.opengis.net/ogc", "And"));
                    OGCFilterTools.moveNodes(children, dstNode);
                } else {
                    dstNode = filter;
                }
            }
            Node propertyIsEqualTo = dstNode.appendChild(doc.createElementNS("http://www.opengis.net/ogc",
                    "PropertyIsEqualTo"));
            propertyIsEqualTo.appendChild(doc.createElementNS("http://www.opengis.net/ogc", "PropertyName"))
                    .appendChild(doc.createTextNode(key));
            propertyIsEqualTo.appendChild(doc.createElementNS("http://www.opengis.net/ogc", "Literal")).appendChild(
                    doc.createTextNode(value));
        }
    }

    /**
     * Move a NodeList to a new existing node within a DOM tree.
     * 
     * 
     * @param srcList
     * @param dstNode
     */
    public static void moveNodes(NodeList srcList, Node dstNode) {
        for (int i = 0; i < srcList.getLength(); i++) {
            if (dstNode != srcList.item(0)) {
                Node n = srcList.item(0).getParentNode().removeChild(srcList.item(0));
                dstNode.appendChild(n);
            }
        }
    }

}
