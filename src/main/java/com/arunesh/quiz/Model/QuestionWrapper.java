package com.arunesh.quiz.Model;

import lombok.Data;

@Data
public class QuestionWrapper {

    private Integer id;
    private String question;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;


    public QuestionWrapper(Integer id, String question, String option_1, String option_2, String option_3, String option_4) {
        this.id = id;
        this.question = question;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;

    }
}
