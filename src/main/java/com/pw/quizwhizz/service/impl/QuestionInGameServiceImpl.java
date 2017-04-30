package com.pw.quizwhizz.service.impl;public class QuestionInGameServiceImpl{com.pw.quizwhizz.repository.QuestionInGameRepository questionInGameRepository;	public QuestionInGameServiceImpl()	{	}@java.lang.Override
    public com.pw.quizwhizz.model.entity.QuestionInGameDTO findQuestionInGameByGameId(java.lang.Long gameId) {
        return questionInGameRepository.findByGameId(gameId);
    }@java.lang.Override
    public java.util.List<com.pw.quizwhizz.model.entity.QuestionInGameDTO> convertToQuestionsInGame(java.util.List<com.pw.quizwhizz.model.entity.Question> questions, java.lang.Long gameId) {
        java.util.List<com.pw.quizwhizz.model.entity.QuestionInGameDTO> questionsInGame = new java.util.ArrayList<com.pw.quizwhizz.model.entity.QuestionInGameDTO>();

        for (int i = 0; i < questions.size(); i++) {
            com.pw.quizwhizz.model.entity.Question q = questions.get(i);
            com.pw.quizwhizz.model.entity.QuestionInGameDTO question = new com.pw.quizwhizz.model.entity.QuestionInGameDTO(q, gameId, i);
            questionsInGame.add(question);
        }
            return questionsInGame;
    }@java.lang.Override
    public void saveQuestionsInGame(java.util.List<com.pw.quizwhizz.model.entity.QuestionInGameDTO> questions) {
        for (com.pw.quizwhizz.model.entity.QuestionInGameDTO question : questions) {
            questionInGameRepository.save(question);
        }
    }}