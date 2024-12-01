package com.example.testwebapp.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.testwebapp.entities.ExtensionQuestion;
import com.example.testwebapp.repositories.ExtensionQuestionRepository;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Pattern;

import com.example.testwebapp.websocket.Message;
import com.example.testwebapp.websocket.WebSocketConfig;

import org.alicebot.ab.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Value("${config.value}")
    String configValue;

    private final boolean TRACE_MODE = false;
    static String botName = "super";

    SimpMessagingTemplate simpMessagingTemplate;
    SimpUserRegistry simpUserRegistry;
    WebSocketConfig websocketConfig;
    ExtensionQuestionRepository extensionQuestionRepository;
    Bot bot;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry, WebSocketConfig websocketConfig, ExtensionQuestionRepository extensionQuestionRepository, Bot bot) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.websocketConfig = websocketConfig;
        this.extensionQuestionRepository = extensionQuestionRepository;
        this.bot = bot;
    }


    @GetMapping("testing")
    public String testing()
            throws Exception {
        System.out.print(123);
        float a = 1.23f, b = 2.3f, c = 10.5f;
        String str1 = "123", str2 = "456";
        String str3, str4, str5;
        str3 = str4 = str5 = "567";

        str1.length();
        int index2 = str1.indexOf("2");

        String str6 = str4 + str5;
        String str7 = str4.concat(str5);
        String str8 = "Escape a \"double-quote\"";


        String[] list = {"string 1", "string 2", "string 3"};
        System.out.println(list.length);

        int numberInteger = 10;
        double numberDouble = numberInteger;

        int numberIntegerTemp = (int) numberDouble;

        return "";
    }

    private String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }

    @GetMapping("extension-question/add/{question}/{answer}")
    public String addExtensionQuestion(@PathVariable String question, @PathVariable String answer) {
        extensionQuestionRepository.insert(new ExtensionQuestion(question, answer));

        return "ok";
    }

    private Element createQuestionAnswerElement(String question, String answer) {

        Element category = new Element("category");

        Element pattern = new Element("pattern");
        pattern.setText(question);

        Element template = new Element("template");
        template.setText(answer);

        category.addContent(pattern);
        category.addContent(template);

        return category;
    }

    @GetMapping("extension-question/file/add/{question}/{answer}")
    public String addExtensionQuestionAiml(@PathVariable String question, @PathVariable String answer) throws IOException, JDOMException {
        String resourcePath = getResourcesPath();
        String extensionQuestionFilePath = String.format("%s/bots/%s/aiml/extension_question.aiml", resourcePath, botName);
        String extensionQuestionCsvFilePath = String.format("%s/bots/%s/aimlif/extension_question.aiml.csv", resourcePath, botName);

        // Update .aiml
        File file = new File(extensionQuestionFilePath);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element rootElement = document.getRootElement();

        Element category = createQuestionAnswerElement(question, answer);

        rootElement.addContent(category);

        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(document, System.out);
        xmlOutputter.output(document, new FileWriter(file));

        // Update .aiml.csv
        BufferedWriter extensionQuestionCsvFileWriter = new BufferedWriter(new FileWriter(extensionQuestionCsvFilePath, true));
        extensionQuestionCsvFileWriter.append(String.format(System.lineSeparator() + "0,%s,*,*,%s,extension_question.aiml", question, answer));
        extensionQuestionCsvFileWriter.close();

        System.out.println("Add question to extension_question.aiml file successfully.");
        return "ok";
    }

    @GetMapping("/question/{question}")
    public String findQuestion(@PathVariable String question) {
        Nodemapper node = this.bot.brain.findNode(new Category(0, question, "*", "*", "nothing", "extension_question.aiml.csv"));
        return node != null ? node.category.getTemplate() : "NOT FOUND";
    }

    @GetMapping("/question/set/{question}/{answer}")
    public String setQuestionAnswer(@PathVariable @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Question must be alphabet, digit, white space only.") String question, @PathVariable @Pattern(regexp = "[a-zA-Z0-9 ]+") String answer) {
        Nodemapper node = this.bot.brain.findNode(new Category(0, question, "*", "*", "nothing", "extension_question.aiml.csv"));
        if (node != null) {
            // update
            node.category.setTemplate(answer);
        } else {
            // create
            this.bot.brain.addCategory(new Category(0, question, "*", "*", answer, "extension_question.aiml"));
        }
        this.bot.writeAIMLIFFiles();
        this.bot.writeAIMLFiles();
        return "ok";
    }


    @GetMapping("testing2/{question}")
    public String testing2(@PathVariable String question) {
        ExtensionQuestion extensionQuestion = extensionQuestionRepository.findByQuestion(question);
        if (extensionQuestion != null) {
            return extensionQuestion.getAnswer();
        }


        String resourcesPath = getResourcesPath();
        System.out.println("resourcesPath = " + resourcesPath);
        MagicBooleans.trace_mode = TRACE_MODE;
        Chat chatSession = new Chat(bot);
        bot.brain.nodeStats();

        String response = chatSession.multisentenceRespond(question);
        while (response.contains("&lt;"))
            response = response.replace("&lt;", "<");
        while (response.contains("&gt;"))
            response = response.replace("&gt;", ">");


        return response;
    }

    // @MessageMapping("/chat/new")
    // @SendTo("/topic/chat")
    // public Message sendMessage(SimpMessageHeaderAccessor header, Message message)
    // throws Exception {
    // System.out.println(header.getUser().getName());
    // return message;
    // }

    @MessageMapping("/chat/message")
    public void sendMessageToUser(SimpMessageHeaderAccessor header, Message message) throws Exception {
        System.out.println("send message to " + message.getTo());
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/queue/message", message);
    }

    // @MessageMapping("/secured/room")
    // public void sendSpeificMessage(Message message) throws Exception {
    // System.out
    // .println("Server received a new message from " + message.getFrom() + ". The
    // message will send to user: "
    // + message.getTo() + " with message content: " + message.getContent());
    // simpMessagingTemplate.convertAndSendToUser(message.getTo(),
    // "/queue/specific-user", message);
    // }

}
