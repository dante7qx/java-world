package format;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import net.hna.dmt.schema.Schema;

/**
 * Model Xml Formater, Please run under JRE 7.x
 * <p>
 * Date: 2012-05-30,09:29:12 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class MF {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Schema s = fromXml(new File("database/datamodel_ocs_infomgr.xml"));
        System.out.println(toXml(s));
    }

    static Schema fromXml(File file) {
        return JAXB.unmarshal(file, Schema.class);
    }

    @SuppressWarnings("unchecked")
    static String toXml(Schema s) {
        try {
            StringWriter sw = new StringWriter();
            // JAXB.marshal(s, sw);
            Class<?> clazz = s.getClass();
            XmlType r = clazz.getAnnotation(XmlType.class);
            JAXBContext context = JAXBContext.newInstance(clazz);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(new JAXBElement(new QName(r.namespace(), r.name()
                .toLowerCase(), ""), clazz, s), sw);
            return sw.toString();
        } catch (Exception e) {

            throw new DataBindingException(e);
        }
    }
}
