package processor;

import annotations.HtmlForm;
import annotations.HtmlInput;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SupportedAnnotationTypes("annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);

        for (Element element : annotatedElements) {
            HtmlForm annotation = element.getAnnotation(HtmlForm.class);
            List<? extends Element> elements = element.getEnclosedElements();
            List<Annotation> fields = new ArrayList<>();
            for (Element element1 : elements) {
                HtmlInput field = element1.getAnnotation(HtmlInput.class);
                if (field != null)
                    fields.add(field);
            }
            createResultFile(annotation, fields);
        }
        return true;
    }

    public void createResultFile(HtmlForm form, List<Annotation> fields) {
        try {
            FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT,
                    "", form.fileName());
            try (PrintWriter printWriter = new PrintWriter(fileObject.openWriter())) {
                printWriter.println(String.format("<form action = \"%s\" method = \"%s\">", form.action(), form.method()));
                for (Annotation field : fields) {
                    HtmlInput input = (HtmlInput)field;
                    printWriter.println(String.format("<input type = \"%s\" name = \"%s\" placeholder = \"%s\">",
                            input.type(), input.name(), input.placeholder()));
                }
                printWriter.println("<input type = \"submit\" value = \"Send\">");
                printWriter.println("</form>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
