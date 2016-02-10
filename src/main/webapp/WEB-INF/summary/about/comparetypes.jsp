<h3 style="width: 100%;">Definition of the compare types</h3>
<ul>
    <li><b>IDENTICAL</b>
        <ul>
            <li>The names of the items are identical (case insensitive)</li>
            <li>The data types of the items are identical</li>
            <li>The set of UMLS codes assigned to the items is identical</li>
            <li>If the items refer to a code list:</li>
            <ul>
                <li>The names of the code lists are identical (case insensitive)</li>
                <li>The data types of the code lists are identical</li>
                <li>The coded values of all code list items are identical (case insensitive)</li>
                <li>The set of UMLS codes of all code list items is identical</li>
            </ul>
        </ul>
    </li>
    <li><b>MATCHING</b> (Data stored for the two items is combinable without transformation)
        <ul>
            <li>The data types of the items are identical
            <li>The set of UMLS codes assigned to the items is identical
            <li>If the items refer to a code list:
                <ul>
                    <li>The data types of the code lists are identical
                    <li>The coded values of all code list items are identical (case insensitive)
                    <li>The set of UMLS codes of all code list items is identical
                </ul>
        </ul>
    </li>
    <li><b>TRANSFORMABLE</b> (Data stored for the two items is combinable using a linear transformation)
    <li>The set of UMLS codes assigned to the items is identical</li>
    <li>If the items refer to a code list:
        <ul>
            <li>The set of UMLS codes of all code list items is identical</li>
        </ul>
    </li>
    <li><b>SIMILAR</b> (The concept domain of the two items is identical)
        <ul>
            <li>The set of UMLS codes assigned to the items is identical</li>
        </ul>
    </li>
    <li><b>DIFFERENT</b> (The concept domain of the two items is different)
    </li>
</ul>