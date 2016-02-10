<p style="width:95%; text-align: justify">
    ODMSummary was developed as an overview and comparison tool for medical forms. 
    Implementation of form comparison is provided for forms according to the Operational Data Model (ODM). 
    ODM is a standard for representation of medical forms and used by various EDC systems that are used for documentation of clinical trials. 
    ODM enables writing study designs for clinical trials in a standardized and machine-readable manner.
</p>
<p style="width:95%; text-align: justify">
    ODMSummary is capable of processing any quantity of ODM files for the comparison. 
    Neither the ODM files themselves nor the information contained in the ODM files will be stored persistantly.
    At first ODMSummary validates the given ODM files against the XML standard and the XML schema definition (XSD) of ODM standard. 
    Thus, forms which are not standard compliant can be identified and errors can be displayed. 
    If the validation was successful further examination of ODM files is possible. 
    The summary includes information about amount of item groups, items and code lists for every form specified within the given ODM files. 
</p>
<p style="width:95%; text-align: justify">
    The compare algorithm passes through all given ODM files and compares every specified item with all other items in the ODM files.
    For every combination of items the algorithm specifies one of the <a href="#" onclick='$("#about").tabs("option", "active", $("comparetypes").index());'>compare types</a>. 
    Thus, we will know how an item compares to every other item in the given set of ODM files. Below you see an exemplary output of an item comparison.
    There is a data item "Patient Age" in the "Example Form 1" which is matching to the data item "Age" in the "Example Form 2". 
    Additional information for the data items can be retrieved by hovering over the names of the data items.
</p>
<table border="1" style="width: 95%; float: left; margin-top: 20px;" rules="all" id="exportMatchingTable">
    <colgroup>
        <col width="250px" span="4">
    </colgroup>
    <tbody><tr>
            <th rowspan="2">Item</th>
            <th rowspan="2">UMLS Codelist</th>
            <th colspan="1" title="Study">Example Study 1</th>
            <th colspan="1" title="Study">Example Study 2</th>
            <th rowspan="2">Number of marked forms</th>
        </tr>
        <tr>
            <th colspan="1" title="Form">
                Example Form 1
            </th>
            <th colspan="1" title="Form">
                Example Form 2
            </th>
        </tr>
        <tr>
            <td>
                <span title="Form: Example Form 1&#10;Unique identifier: OID_02&#10;Datatype: integer">
                    Name: Patient Age
                </span>
            </td>
            <td>
                <span onmouseover="showTooltip($(this))" title="Age">C0001779</span>
            </td>
            <td></td>
            <td>
                <span title="Unique identifier: OID_01">
                    Name: Age
                </span>
            </td>
            <td>1</td>
        </tr>
        <tr>
            <td>
                <span title="Form: Example Form 2&#10;Unique identifier: OID_01&#10;Datatype: integer">
                    Name: Age
                </span>
            </td>
            <td>
                <span onmouseover="showTooltip($(this))" title="Age">C0001779</span>
            </td>
            <td>
                <span title="Unique identifier: OID_02">
                    Name: Patient Age
                </span>
            </td>
            <td colspan="1"></td>
            <td>1</td>
        </tr>
    </tbody></table>