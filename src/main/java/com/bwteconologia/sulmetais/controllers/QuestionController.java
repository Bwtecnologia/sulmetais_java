package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.*;
import com.bwteconologia.sulmetais.models.*;
import com.bwteconologia.sulmetais.services.AnswerService;
import com.bwteconologia.sulmetais.services.QuestionPositionService;
import com.bwteconologia.sulmetais.services.QuestionService;
import com.bwteconologia.sulmetais.services.QuizService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    QuestionPositionService questionPositionService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuizService quizService;

    @GetMapping(value = "/questions")
    public ResponseEntity<List<QuestionModel>> getAllQuestions() {
        List<QuestionModel> question = questionService.getAllQuestions();
        return ResponseEntity.ok(question);
    }

    @GetMapping(value = "/questions/{id}")
    public ResponseEntity<QuestionModel> findById(@PathVariable("id") Long id) {
        QuestionModel question = questionService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuestionNotFoundException("Question with " + id + " is Not Found!"));
        return ResponseEntity.ok().body(question);
    }

    @PostMapping(value = "/questions")
    public ResponseEntity<QuestionModel> addQuestion(@RequestBody QuestionModel question) {
        Optional<QuestionModel> existsQuestion = questionService.findByDescription(question.getDescription());
        if (existsQuestion.isPresent()) {
            throw new QuestionAlreadyExistsException("Question Already Exists");
        }
        QuestionModel savedQuestion = questionService.save(question);
        return ResponseEntity.ok(savedQuestion);
    }

    @PutMapping(value = "/questions/{id}")
    public ResponseEntity<QuestionModel> updateQuestion(@PathVariable("id") Long id, @RequestBody QuestionModel questionUpdated) {
        QuestionModel question = questionService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuestionNotFoundException("Question with " + id + " is Not Found!"));
        question.setType(questionUpdated.getType());
        question.setDescription(questionUpdated.getDescription());
        question.settBodyFormula(questionUpdated.getBodyFormula());
        question.setUpdatedAt(new Date());

        QuestionModel updatedQuestion = questionService.save(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping(value = "/questions/{id}")
    public ResponseEntity<ResponseObjectModel> deleteQuestion(@PathVariable("id") Long id) {
        QuestionModel question = questionService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuestionNotFoundException("Question with " + id + " is Not Found!"));
        questionService.deleteById(Math.toIntExact(id));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Question with ID :" + id + " is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }

    @GetMapping("/questions/{id}/answers")
    public ResponseEntity<Map<String, Object>> getQuestionAndAnswersById(@PathVariable Long id) {
        Optional<QuestionModel> optionalQuestion = questionService.findById(Math.toIntExact(id));
        if (optionalQuestion.isPresent()) {
            QuestionModel question = optionalQuestion.get();
            List<AnswerModel> answers = question.getAnswers();

            Map<String, Object> result = new HashMap<>();
            result.put("question", question);
            result.put("answers", answers);

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/questions/positionmodel")
    public ResponseEntity<List<QuestionPositionModel>> getQuestionPositionById(@PathParam(value = "quizId") Long quizId) {

        Optional<QuizModel> quizModelOptional = quizService.findById(Math.toIntExact(quizId));
        if (quizModelOptional.isEmpty())
            throw new QuizNotFoundException("The quiz with ID: " + quizId + " don't exists!");

        Optional<List<QuestionPositionModel>> optionalQuestionPositionModels = questionPositionService.findAllQuestionsByQuizId(quizId);

        if (optionalQuestionPositionModels.isEmpty())
            throw new QuestionNotFoundException("The quiz with ID: " + quizId + " don't have questions!");


        return ResponseEntity.ok(optionalQuestionPositionModels.get());
    }

    @GetMapping("/questions/position")
    public ResponseEntity<List<QuestionModel>> returnAllByQuizOrderByPosition(@RequestParam Long quizId) {

        Optional<QuizModel> quizModelOptional = quizService.findById(Math.toIntExact(quizId));
        if (quizModelOptional.isEmpty())
            throw new QuizNotFoundException("The quiz with ID: " + quizId + " don't exists!");

        Optional<List<QuestionPositionModel>> optionalQuestionPositionModels = questionPositionService.findAllQuestionsByQuizId(quizId);

        if (optionalQuestionPositionModels.isEmpty())
            throw new QuestionNotFoundException("The quiz with ID: " + quizId + " don't have questions!");

        List<QuestionModel> questionModels = new ArrayList<>();

        for (QuestionPositionModel questionPositionModel : optionalQuestionPositionModels.get()) {

            Optional<QuestionModel> questionModelOptional = questionService.findById(Math.toIntExact(questionPositionModel.getQuestion().getId()));
            if (questionModelOptional.isEmpty())
                throw new QuestionNotFoundException("The question with ID: " + questionPositionModel.getId() + " don't exists!");

            questionModels.add(questionModelOptional.get());
        }

        return ResponseEntity.ok(questionModels);
    }

    @DeleteMapping("/questions/positionmodel/{id}")
    public ResponseEntity<String> deletePositionById(@PathVariable Long id) {

        Optional<QuestionPositionModel> questionPositionModelOptional = questionPositionService.findById(id);
        if (questionPositionModelOptional.isEmpty())
            throw new QuestionNotFoundException("The question position with ID: " + id + " don't exists!");

        questionPositionService.deleteById(id);

        return ResponseEntity.ok("The question position with ID: " + id + " was deleted!");
    }

    @Transactional
    @PostMapping("/questions/position")
    public ResponseEntity<List<QuestionPositionModel>>
    insertPositionsForQuestions(@RequestParam Long quizId,
                                @RequestBody List<QuestionPositionModel> questionPositionModels) {

        Optional<QuizModel> quizModelOptional = quizService.findById(Math.toIntExact(quizId));
        if (quizModelOptional.isEmpty())
            throw new QuizNotFoundException("The quiz whid ID: " + quizId + " don't exists!");

        List<QuestionPositionModel> positionList = new ArrayList<>();

        try {
            questionPositionService.deleteAllByQuiz(quizModelOptional.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (QuestionPositionModel position : questionPositionModels) {

            Optional<QuestionModel> questionModelOptional = questionService.findById(Math.toIntExact(position.getQuestion().getId()));
            if (questionModelOptional.isEmpty())
                throw new QuestionNotFoundException("The question whid ID: " + position.getId() + " don't exists!");

            Optional<QuestionPositionModel> positionOptionalToExclude = questionPositionService.findByPositionAndQuizAndQuestion
                    (position.getPosition(), quizModelOptional.get(), questionModelOptional.get());
            if (positionOptionalToExclude.isPresent())
                questionPositionService.deleteById(positionOptionalToExclude.get().getId());

            position.setQuiz(quizModelOptional.get());
            position.setQuestion(questionModelOptional.get());
            positionList.add(position);
        }

        try {
            questionPositionService.deleteAllByQuiz(quizModelOptional.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (QuestionPositionModel position : positionList) {
            questionPositionService.save(position);
        }

        return ResponseEntity.ok(positionList);
    }

    @PostMapping("/questions/{id}/formula")
    public ResponseEntity<QuestionModel> insertFormulaInQuestion
            (@PathVariable Long id,
             @PathParam(value = "quizId") Long quizId,
             @RequestBody List<BodyFormulaQuestionModel> bodyFormula) {

        Optional<QuestionModel> optionalQuestion = questionService.findById(Math.toIntExact(id));
        if (optionalQuestion.isEmpty()) throw new QuestionNotFoundException("Question with " + id + " is Not Found!");


        Optional<QuizModel> optionalQuiz = quizService.findById(Math.toIntExact(quizId));
        if (optionalQuiz.isEmpty()) throw new QuizNotFoundException("Quiz with " + id + " is Not Found!");

        //verify if bodyformula almost one item in json
        Optional<List<BodyFormulaQuestionModel>> optionalBodyFormula = Optional.ofNullable(bodyFormula);
        if (optionalBodyFormula.isEmpty())
            throw new FormulaQuestionWrongAddException("Body question formula cannot be null");
        List<BodyFormulaQuestionModel> bodyFormulaQuestion = optionalBodyFormula.get();
        if (bodyFormulaQuestion.isEmpty())
            throw new FormulaQuestionWrongAddException("You need one body formula to add formula");

        for (BodyFormulaQuestionModel body : bodyFormula) {

            if (body.getFormulas() == null)
                throw new FormulaQuestionWrongAddException("Body Formula is in incorrect format");
            body.setQuestionId(id);
            if (body.getFormulas().isEmpty())
                throw new FormulaQuestionWrongAddException("You cannot have a body formula without 1 formula!");

            //verify if exists at least one answer in bodyformula
            Optional<List<AnswerModel>> optionalAnswerInBodyFormula = Optional.ofNullable(body.getAnswersIfTrue());
            if (optionalAnswerInBodyFormula.isEmpty()) throw new FormulaQuestionWrongAddException
                    ("To register one answer you need one answer if it's true");
            List<AnswerModel> questionInBodyFormula = optionalAnswerInBodyFormula.get();
            if (questionInBodyFormula.isEmpty()) throw new FormulaQuestionWrongAddException
                    ("To register one answer you need one answer if it's true");

            for (FormulasQuestionModel formula : body.getFormulas()) {

                if (formula.getQuestion().getId().equals(id)) throw new
                        FormulaQuestionWrongAddException("The formula can't refer to the father question.");

                formula.setBodyFormulaId(id);

                //verifica se essa question está dentro do quiz
                boolean questionIsInTheQuiz = false;
                for (QuestionPositionModel question : optionalQuiz.get().getQuestionPosition()) {
                    if (question.getId() == formula.getQuestion().getId()) {
                        questionIsInTheQuiz = true;
                        break;
                    }

                }
                if (!questionIsInTheQuiz)
                    throw new FormulaQuestionWrongAddException("This question is not in the referred Quiz ID!");

            }
        }
        QuestionModel question = optionalQuestion.get();

        List<BodyFormulaQuestionModel> bodyFormulaListForSave = question.getBodyFormula();
        for(BodyFormulaQuestionModel body : bodyFormula) {
            bodyFormulaListForSave.add(body);
        }

        question.settBodyFormula(bodyFormulaListForSave);
        question.setFormula(true);
        questionService.save(question);

        return ResponseEntity.ok(question);
    }

    @GetMapping("/questions/{id}/formula/calculator")
    public ResponseEntity<List<AnswerModel>> returnResultOfFormulaCalc
            (@PathVariable Long id, @PathParam("quizId") int quizId, @RequestBody List<AnswerQuizModel> answersQuiz) {


        Optional<QuestionModel> optionalQuestion = questionService.findById(Math.toIntExact(id));
        if (optionalQuestion.isEmpty()) throw new QuestionNotFoundException("Question with " + id + " is Not Found!");
        QuestionModel question = optionalQuestion.get();
        if (!question.isFormula())
            throw new QuestionNotFoundException("This question doesn't have formula to calculate!");
        Optional<QuizModel> optionalQuiz = quizService.findById(quizId);
        if (optionalQuiz.isEmpty()) throw new QuizNotFoundException("The quiz with ID: " + quizId + " don't exists!");


        if (answersQuiz.isEmpty()) throw new FormulaQuestionErrorInCalculatingException
                ("You need to pass the answers of quiz to get the response for formula!");
        List<AnswerModel> answerList = question.getAnswers();

        List<BodyFormulaQuestionModel> bodyFormulaFilteredList = question.getBodyFormula()
                .stream()
                .filter(quizIdFromFormula -> quizIdFromFormula.getQuizId() == quizId).toList();

        //first see OR
        for (BodyFormulaQuestionModel bodyFormula : question.getBodyFormula()) {

            String operationOr = "or";
            String operationIf = "and";

            boolean isAllAswerCorrect = true;

            for (FormulasQuestionModel formula : bodyFormula.getFormulas()
                    .stream()
                    .filter(formulas -> formulas.getType().equals(operationIf))
                    .toList()) {

                boolean questionExistsInQuiz = false;
                //overwrite old answerlist from the question to the associated in bodyFormula if it's true
                for (AnswerQuizModel answer : answersQuiz) {
                    if (answer.getQuestion().getId().equals(formula.getQuestion().getId())) {
                        questionExistsInQuiz = true;
                        if (!answer.getAnswer().getId().equals(formula.getAnswer().getId())) {
                            isAllAswerCorrect = false;
                        }
                    }
                }

                if (!questionExistsInQuiz) isAllAswerCorrect = false;
            }

            if (isAllAswerCorrect) answerList = bodyFormula.getAnswersIfTrue();

            for (FormulasQuestionModel formula : bodyFormula.getFormulas()
                    .stream()
                    .filter(formulas -> formulas.getType().equals(operationOr))
                    .toList()) {

                //overwrite old answerlist from the question to the associated in bodyFormula if it's true
                for (AnswerQuizModel answerQuiz : answersQuiz) {
                    if (answerQuiz.getQuestion().getId() == formula.getQuestion().getId()) {
                        if (answerQuiz.getAnswer().getId() == formula.getAnswer().getId()) {
                            answerList = bodyFormula.getAnswersIfTrue();
                        }
                    }
                }
            }


        }


        return ResponseEntity.ok(answerList);
        //question.getFormula();
    }

}

