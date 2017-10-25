package com.demo.word;

import com.aspose.words.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word2HTML {

    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Word2HTML.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Element deletePageNode(Element body) {
        Elements select = body.select("div div div p span");
        for (Element e : select) {
            if ("1".equals(e.text())) {
                e.remove();
            }
        }
        List<Node> childNodes = body.childNodes();// 去掉另外一种分页符
        for (Node n : childNodes) {
            if ("div".equals(n.nodeName())) {
                List<Node> childNodes2 = n.childNodes();
                for (Node n1 : childNodes2) {
                    if ("div".equals(n1.nodeName())) {
                        List<Node> childNodes3 = n1.childNodes();
                        for (Node n2 : childNodes3) {
                            if ("p".equals(n2.nodeName())) {
                                List<Node> childNodes4 = n2.childNodes();
                                for (Node n3 : childNodes4) {
                                    if ("span".equals(n3.nodeName())) {
                                        if ("1".equals(((Element) (n3)).text().trim())) {
                                            n3.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return body;
    }

    private static String getDocxHTMLContext(String filepath, String savepath) {
        getLicense();
        Map<String, String> m = new HashMap<String, String>();
        try {
            Document doc = new Document(filepath);
            @SuppressWarnings("unchecked")
            NodeCollection<DrawingML> shapes = doc.getChildNodes(NodeType.DRAWING_ML, true);
            int ii = 0;
            for (DrawingML shape : shapes) {
                if (shape.hasImage()) {
                    DrawingMLImageData i = shape.getImageData();
                    String type = FileFormatUtil.imageTypeToExtension(i.getImageType());
                    String path = "D:/" + savepath;
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String imageName = new Date().getTime() + "_" + type;
                    String imagePath = savepath + imageName;
                    String realPath = path + imageName;
                    i.save(realPath);
                    File f = new File(realPath);
                    dealPictures(doc, m, ii, shape, imagePath, f);
                    ii++;
                }
            }
            String html = doc.toString(SaveFormat.HTML);
            if (m.size() > 0) {
                for (String key : m.keySet()) {
                    html = html.replace(key, m.get(key));
                }
            }
            org.jsoup.nodes.Document parse = Jsoup.parse(html);
            Element body = deletePageNode(parse.body());
            return body.html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDocHTMLContext(String filepath, String savepath) {
        getLicense();
        Map<String, String> m = new HashMap<String, String>();
        try {
            Document doc = new Document(filepath);
            @SuppressWarnings("unchecked")
            NodeCollection<Shape> shapes = doc.getChildNodes(NodeType.SHAPE, true);
            int ii = 0;
            for (Shape shape : shapes) {
                if (shape.hasImage()) {
                    ImageData i = shape.getImageData();
                    String type = FileFormatUtil.imageTypeToExtension(i.getImageType());
                    String path = "D:/" + savepath;
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String imageName = new Date().getTime() + "_" + type;
                    String imagePath = savepath + imageName;
                    String realPath = path + imageName;
                    i.save(realPath);
                    File f = new File(realPath);
                    dealPictures(doc, m, ii, shape, imagePath, f);
                    ii++;
                }
            }
            String html = doc.toString(SaveFormat.HTML);
            if (m.size() > 0) {
                for (String key : m.keySet()) {
                    html = html.replace(key, m.get(key));
                }
            }
            org.jsoup.nodes.Document parse = Jsoup.parse(html);
            Element body = deletePageNode(parse.body());
            return body.html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void dealPictures(Document doc, Map<String, String> map, int index, com.aspose.words.Node shape,
                                     String imagePath, File f) throws Exception {
        if (f.exists()) {
            DocumentBuilder builder = new DocumentBuilder(doc); // 新建文档节点
            builder.moveTo(shape); // 移动到图片位置
            builder.write("_DEMO_" + index); // 插入替换文本
            map.put("_DEMO_" + index, "<img src='" + imagePath + "' />");
            shape.remove(); // 移除图形
        }
    }

    public static String getHTML(String path, String savepath) {
        File f = new File(path);
        if (f.exists()) {
            String ext = path.substring(path.lastIndexOf(".") + 1);
            if (ext.equalsIgnoreCase("docx")) {
                return getDocxHTMLContext(path, savepath);
            } else if (ext.equalsIgnoreCase("doc")) {
                return getDocHTMLContext(path, savepath);
            }
        }
        return "";
    }
}
