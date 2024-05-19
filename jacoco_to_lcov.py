import xml.etree.ElementTree as ET
import sys

def jacoco_to_lcov(xml_path, lcov_path):
    tree = ET.parse(xml_path)
    root = tree.getroot()

    with open(lcov_path, "w") as lcov_file:
        for package in root.findall(".//package"):
            for class_ in package.findall("class"):
                filename = package.get("name") + "/" + class_.get("name").replace("/", ".") + ".java"
                lcov_file.write(f"SF:{filename}\n")
                for counter in class_.findall("counter"):
                    if counter.get("type") == "LINE":
                        lcov_file.write(f"DA:{counter.get('missed')},{counter.get('covered')}\n")
                lcov_file.write("end_of_record\n")

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: jacoco_to_lcov.py <input.xml> <output.lcov>")
        sys.exit(1)

    jacoco_to_lcov(sys.argv[1], sys.argv[2])