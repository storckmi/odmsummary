package de.imi.odmtoolbox.library;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import de.imi.odmtoolbox.model.ODMStudy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class provides methods to parse and validate elements from an
 * Operational Data Model (ODM) file.
 *
 */
@Service
public class ODMParser {

    @Autowired
    ServletContext servletContext;

    @Autowired
    private UMLSCodeDao umlsCodeDao;
    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder;
    private List<SAXException> parseErrors = new ArrayList<>();

    public ODMParser() {
        documentBuilderFactory.setNamespaceAware(true);
        try {
            this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }

    }

    /**
     * Renames the XML namespace for the given {@link Node} and recursively for
     * all child nodes.
     *
     * @param node The node for which the namespace should be changed.
     * @param namespace The new name of the XML namespace.
     */
    public static void renameNamespaceRecursive(Node node, String namespace) {
        Document document = node.getOwnerDocument();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            document.renameNode(node, namespace, node.getNodeName());
        }
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            renameNamespaceRecursive(list.item(i), namespace);
        }
    }

    /**
     * Parses a given ODm file into a {@link Document} and collects the errors.
     *
     * @param odmFile The ODM file which should be parsed.
     * @return The parsed {@link Document}.
     */
    public Document parseODMFile(MultipartFile odmFile) {
        Document doc = null;
        try {
            doc = documentBuilder.parse(odmFile.getInputStream());
        } catch (SAXException parseError) {
            addError(parseError);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return doc;
    }

    /**
     * Validates a given {@link Document} and retruns all errors.
     *
     * @param document The {@link Document} which should be validated.
     * @return The list of errors for the given {@link Document}.
     */
    public List<SAXException> isValid(Document document) {
        try {
            File schemaLocation = null;
            Element odmElement = (Element) document.getElementsByTagName("ODM").item(0);
            switch (odmElement.getAttribute("ODMVersion")) {
                case "1.3":
                case "1.3.1":
                    schemaLocation = new File(servletContext.getRealPath("xsd") + "/odm1-3-1/ODM1-3-1.xsd");
                    break;
                case "1.3.2":
                    schemaLocation = new File(servletContext.getRealPath("xsd") + "/odm1-3-2/ODM1-3-2.xsd");
                    break;
                default:
                    throw (new SAXParseException("ODM Version " + odmElement.getAttribute("ODMVersion") + " is not supported", null, null, -1, -1));
            }

            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = schemaFactory.newSchema(schemaLocation);
            documentBuilderFactory.setSchema(schema);
            Validator schemaValidator = schema.newValidator();
            schemaValidator.setErrorHandler(new ODMErrorHandler(this));

            DOMSource source = new DOMSource(document);
            schemaValidator.validate(source);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SAXException saxException) {
            this.addError(saxException);
        }
        return clearAndReturnParseErrors();

    }

    public ODMStudy getStudy(Document doc) {
        return ODMStudy.parseStudyFromDocument(doc, this.umlsCodeDao);
    }

    protected void addError(SAXException parseError) {
        this.parseErrors.add(parseError);
    }

    private List<SAXException> getParseErrors() {
        return this.parseErrors;
    }

    private List<SAXException> clearAndReturnParseErrors() {
        List<SAXException> documentExceptions = new ArrayList<>(getParseErrors());
        this.parseErrors.clear();
        return documentExceptions;
    }
}
