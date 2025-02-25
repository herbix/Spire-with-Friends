package chronoMods.coop.hardmode;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BulkUpPower extends AbstractPower {
  public static final String POWER_ID = "BulkUp";
  
  public BulkUpPower(AbstractCreature owner) {
    this(owner, 1);
  }
  
  public BulkUpPower(AbstractCreature owner, int amt) {
    this.name = StrangeFlame.DESCRIPTIONS[11];
    this.ID = "BulkUp";
    this.owner = owner;
    this.amount = amt;
    this.basePower = amt;
    updateDescription();
    loadRegion("controlled_change");
  }
  
  public void updateDescription() {
    this.description = StrangeFlame.DESCRIPTIONS[12];
  }
  
  public void atEndOfTurn(boolean isPlayer) {
    if (this.owner.isPlayer)
      return; 
    energy = 0;
    this.amount = this.basePower;
    updateDescription();
  }
  
  public void atEndOfRound() {
    if (!this.owner.isPlayer)
      return; 
    this.amount = this.basePower;
    updateDescription();
  }
  

  public int energy = 0;

  public void onAfterUseCard(AbstractCard card, UseCardAction action) {
      addToBot(new GainBlockAction(this.owner, this.owner, this.amount));

      energy += card.costForTurn;
      while (energy > 3) {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
        energy -= 4;
      }

      flash();
      this.amount++;
      updateDescription();
  }
  
  public void stackPower(int stackAmount) {
    this.amount += stackAmount;
    this.basePower += stackAmount;
  }
    
  private int basePower;
}
