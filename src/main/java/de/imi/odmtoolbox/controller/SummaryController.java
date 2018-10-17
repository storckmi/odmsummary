package de.imi.odmtoolbox.controller;

import de.imi.odmtoolbox.comparator.FormPerStudyComparator;
import de.imi.odmtoolbox.comparator.StudyComparator;
import de.imi.odmtoolbox.dao.UMLSCodeDao;
import de.imi.odmtoolbox.library.MultiPartFileUploadBean;
import de.imi.odmtoolbox.library.ODMCompare;
import de.imi.odmtoolbox.library.ODMCompareTableStructure;
import de.imi.odmtoolbox.library.ODMParser;
import de.imi.odmtoolbox.library.UMLSCodeService;
import de.imi.odmtoolbox.model.ODMCompareType;
import de.imi.odmtoolbox.model.ODMComparedItem;
import de.imi.odmtoolbox.model.ODMForm;
import de.imi.odmtoolbox.model.ODMStudy;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * The Controller servlet communicates with the front end of the model and loads
 * the HttpServletRequest or HttpSession with appropriate data, before
 * forwarding the HttpServletRequest and Response to the JSP using a
 * RequestDispatcher.
 *
 */
@Controller
public class SummaryController {

    @Autowired
    private ODMParser odmParser;
    @Autowired
    private UMLSCodeDao umlsCodeDao;
    @Autowired
    private UMLSCodeService umlsCodeService;

    @RequestMapping(value = "/summary")
    public String uploadFiles() {
        return "summary/mainsummary";
    }

    /**
     * Compares the items in the given ODM files pairwise and returns the
     * results.
     *
     * @param imageFiles The ODM files for the comparison.
     * @param model The model, which holds information for the view.
     * @return The view showing the results of the comparison.
     */
    @RequestMapping(value = "/summary", method = RequestMethod.POST)
    public String summarizeODMFiles(MultiPartFileUploadBean imageFiles, Model model) throws ParseException {
        Map<String, ODMStudy> studies = new TreeMap<>();
        Map<String, List<SAXException>> fileErrors = new TreeMap<>();
        Comparator<ODMStudy> studyComparator = new StudyComparator();
        Map<ODMStudy, Set<ODMForm>> studyForms = new TreeMap<>(studyComparator);
        Comparator<ODMForm> formComparator = new FormPerStudyComparator();
        TreeSet<ODMForm> forms = new TreeSet<>(formComparator);
        // Get all new files
        List<MultipartFile> files = imageFiles.getFiles();
        if (files != null && !files.isEmpty()) {
            for (Object object : files) {
                if (object != null) {
                    MultipartFile uploadFile = (MultipartFile) object;
                    if (uploadFile.isEmpty() == false) {
                        // Parse the files to XML documents
                        Document doc = null;
                        try {
                            doc = odmParser.parseODMFile(uploadFile.getInputStream());
                        } catch (IOException ex) {
                            Logger.getLogger(SummaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        List<SAXException> parseErrors = odmParser.isValid(doc);
                        if (parseErrors.isEmpty() && doc != null) {
                            ODMStudy study = ODMStudy.parseStudyFromDocument(doc, umlsCodeDao);
                            String studyName = study.getName();
                            Integer counter = 1;
                            // In case of equal study names 
                            while (studyForms.containsKey(study) == true) {
                                // Change the study name to studyname (counter)
                                study.setName(studyName + " (" + counter.toString() + ")");
                                counter++;
                            }
                            studies.put(uploadFile.getOriginalFilename(), study);
                            studyForms.put(study, study.getForms());
                            forms.addAll(study.getForms());
                        } else {
                            fileErrors.put(uploadFile.getOriginalFilename(), parseErrors);
                        }
                    }
                }
            }
        }

        Map<ODMCompareType, TreeSet<ODMComparedItem>> comparedItems = ODMCompare.compareODMItems(forms);
        if (comparedItems != null && !comparedItems.isEmpty()) {
            ODMCompareTableStructure tableStructure = new ODMCompareTableStructure(comparedItems);
            model.addAttribute("tableStructure", tableStructure);
        }
        model.addAttribute("fileErrors", fileErrors);
        model.addAttribute("studies", studies);
        model.addAttribute("studyForms", studyForms);
        model.addAttribute("comparedItems", comparedItems);
        return "summary/mainsummary";
    }

    /**
     * Get the HTML presentation for the given UMLS code.
     *
     * @param umlsCode The UMLS code for which the additional information should
     * be displayed as HTML.
     * @return The HTML representation of the additional information for the
     * given UMLS code.
     */
    @RequestMapping(value = "/getUMLSCode", method = RequestMethod.POST)
    public @ResponseBody
    String getUMLSHTMLPresentation(@RequestParam String umlsCode) {
        return umlsCodeService.getHTMLPresentation(umlsCode);
    }
}
