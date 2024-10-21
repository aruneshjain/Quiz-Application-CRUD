package com.arunesh.quiz.Service;

import com.arunesh.quiz.DAO.QuestionDAO;
import com.arunesh.quiz.DAO.QuizDAO;
import com.arunesh.quiz.Model.QuestionWrapper;
import com.arunesh.quiz.Model.Questions;
import com.arunesh.quiz.Model.Quiz;
import com.arunesh.quiz.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDAO quizDao;
    @Autowired
    QuestionDAO questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Questions> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Questions> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for(Questions q : questionFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestion(), q.getOption_1(), q.getOption_2(), q.getOption_3(), q.getOption_4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();
        List<Questions> questions = quiz.getQuestions();
        int right =0; int i=0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

}
