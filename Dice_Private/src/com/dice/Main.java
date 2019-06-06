/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dice;

import java.awt.Color;
import java.awt.Graphics2D;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.script.listener.AdvancedMessageListener;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Dice_Bot", version = 1.0, description = "55x Dice", category = Category.MINIGAME)

public class Main extends AbstractScript implements AdvancedMessageListener {
    
    private Timer timer;
    private boolean spam;
    private boolean roll;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.STRENGTH);
               log("Initialized");
            
        }
            
            public void Trade() {
                sleep(7000);
                Item[] theirItems = getTrade().getTheirItems();
                     if (theirItems != null) {
                for (int i = 0; i < theirItems.length; i++) {
                    Item theirItem = theirItems[i];
                    if (theirItem.getName().contains("Coins")) {
                        log("Coins amount " + theirItem.getAmount());
                    getTrade().acceptTrade();
                    roll = true;
            }}}}

        @Override
	public void onTradeMessage(Message msg) {
        String trader = msg.getUsername();
        log("Incomming trade from : " + trader);
        getTrade().tradeWithPlayer(trader);
        }
        
    @Override
public void onMessage(Message msg) {    
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
}
        @Override
    public void onAutoMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrivateInfoMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onClanMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onGameMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPlayerMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrivateInMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrivateOutMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
	public void onStart() {
            init();
		log("Welcome to Dice Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Bank!.");
	}


	private enum State {
               SPAM, TRADE, ROLL_DICE, SLEEP
	};

	private State getState() {
            if (getTrade().isOpen()) {
                log("State = TRADE");
                return State.TRADE;
            }
            if (roll) {
                log("State = ROLL");
                return State.ROLL_DICE;
            }
        
          return State.TRADE;
}
        
        
	@Override
	public int onLoop() {
		switch (getState()) {
                    case SPAM:
                        break;
                    case TRADE:
                     Trade();
                        break;
                    case ROLL_DICE:
                     
                   break;
                }
		return Calculations.random(300, 700);
        }
        
@Override
	public void onPaint(Graphics2D g1){
             //public void onPaint(Graphics g){
            //Graphics2﻿D g = (Graphics2D) g1;
                        g1.setColor(Color.RED);
			//g.drawString("State: " + getState().toString(), 10, 35);
			//g.drawString("State: " + getState().toString(), 10, 35);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g1.drawString("Profit (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 65);
                        g1.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 80);
                        g1.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 95);
                        g1.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 110);
                        g1.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 125);
                                            
                       
}}
