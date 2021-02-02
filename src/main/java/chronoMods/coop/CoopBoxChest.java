package chronoMods.coop.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rewards.chests.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.core.*;

import chronoMods.*;

public class CoopBoxChest extends AbstractChest {
  public CoopBoxChest() {
    this.img = ImageMaster.M_CHEST;
    this.openedImg = ImageMaster.M_CHEST_OPEN;
    this.hb = new Hitbox(256.0F * Settings.scale, 270.0F * Settings.scale);
    this.hb.move(CHEST_LOC_X, CHEST_LOC_Y - 90.0F * Settings.scale);

    this.GOLD_AMT = 10;
  }

  public void open() {
    AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
    CardCrawlGame.sound.play("CHEST_OPEN");

    // Gold Chance
    if (AbstractDungeon.treasureRng.random(0,100) < 80)
        AbstractDungeon.getCurrRoom().addGoldToRewards(Math.round(AbstractDungeon.treasureRng.random(this.GOLD_AMT * 0.9F, this.GOLD_AMT * 1.1F)));

    // Card Chance
    if (AbstractDungeon.treasureRng.random(0,100) < 50) {
        AbstractDungeon.getCurrRoom().addCardToRewards();        
    }

    // Colourless card chance
    if (AbstractDungeon.treasureRng.random(0,100) < 30) {
        RewardItem r = new RewardItem(AbstractCard.CardColor.COLORLESS);
        if (r.cards.size() > 1)
            r.cards.subList(1, r.cards.size()).clear();
        if (r.cards.size() > 0)
            AbstractDungeon.getCurrRoom().rewards.add(r);
    }

    // Potion Chance
    if (AbstractDungeon.treasureRng.random(0,100) < 10)
        AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion());

    // Relic Chance
    if (AbstractDungeon.treasureRng.random(0,100) < 5)
        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
  }
}