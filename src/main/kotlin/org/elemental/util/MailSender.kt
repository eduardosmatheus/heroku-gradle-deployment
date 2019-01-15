package org.elemental.util

import org.springframework.context.annotation.PropertySource
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.TemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import java.io.File
import javax.mail.internet.MimeMessage


@PropertySource("classpath:application.properties")
class MailSender(private val username: String, private val password: String) {
    private val javaMailSenderImpl: JavaMailSenderImpl = JavaMailSenderImpl()
    @Autowired
    private val templateEngine: TemplateEngine? = null
    private val host: String? = null
    init {
        javaMailSenderImpl.host = ""
        javaMailSenderImpl.port = 587
        javaMailSenderImpl.username = username
        javaMailSenderImpl.password = password
        javaMailSenderImpl.javaMailProperties.setProperty("smtp.auth", "true")
        javaMailSenderImpl.javaMailProperties.setProperty("smtp.starttls.enable", "true")
    }

    fun send(from: String, to: String, subject: String, body: String, file: File? = null) {
        val messagePreparator = { mimeMessage: MimeMessage ->
            val messageHelper = MimeMessageHelper(mimeMessage, true)
            messageHelper.setFrom(from)
            messageHelper.setTo(to)
            messageHelper.setSubject(subject)
            if (file != null) messageHelper.addAttachment(file.name, file)
            messageHelper.setText(body, true)
        }
        javaMailSenderImpl.send(messagePreparator)
    }

    fun send(from: String, to: Array<String>, subject: String, body: String, file: File? = null) {
        val messagePreparator = { mimeMessage: MimeMessage ->
            val messageHelper = MimeMessageHelper(mimeMessage, true)
            messageHelper.setFrom(from)
            messageHelper.setTo(to)
            messageHelper.setSubject(subject)
            if (file != null) messageHelper.addAttachment(file.name, file)
            messageHelper.setText(body, true)
        }
        javaMailSenderImpl.send(messagePreparator)
    }
}