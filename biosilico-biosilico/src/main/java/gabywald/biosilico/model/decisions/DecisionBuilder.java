package gabywald.biosilico.model.decisions;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.reproduction.ReproductionAnima;
import gabywald.biosilico.model.reproduction.ReproductionBacta;
import gabywald.biosilico.model.reproduction.ReproductionDaemon;
import gabywald.biosilico.model.reproduction.ReproductionHelper;
import gabywald.biosilico.model.reproduction.ReproductionViridita;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Builder for IDecision. 
 * @author Gabriel Chandesris (2020)
 */
public class DecisionBuilder {
	private DecisionType type	= null;		
	private Organism orga		= null;
	private Integer	object		= null, 
					threshold	= null, 
					attribute	= null, 
					variable	= null, 
					value		= null;
	
	public DecisionBuilder() { ; }
	
	public DecisionBuilder type(DecisionType type) {
		this.type = type;
		return this;
	}
	
	public DecisionBuilder organism(Organism orga) {
		this.orga = orga;
		return this;
	}
	
	public DecisionBuilder object(int object) {
		this.object = object;
		return this;
	}
	
	public DecisionBuilder threshold(int threshold) {
		this.threshold = threshold;
		return this;
	}
	
	public DecisionBuilder attribute(int attribute) {
		this.attribute = attribute;
		return this;
	}
	
	public DecisionBuilder variable(int variable) {
		this.variable = variable;
		return this;
	}
	
	public DecisionBuilder value(int value) {
		this.value = value;
		return this;
	}
	
	public IDecision build() {
		if (this.type == null) { return null; }
		if (this.orga == null) { return null; }
		
		IDecision what2do = null;
		// NOTE : attention ! start from 850 according to chemical list !!
		switch(this.type) {
		case STAY : 	what2do = new DecisionToMove(this.orga, DirectionWorld.CURRENT.getIndex());break;
		
		// ***** Decision to push (act1) an agent. 
		case PUSH: 		what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::push);break;
		
		// ***** Decision to pull (act2) an agent.
		case PULL: 		what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::pull);break;
		
		// ***** Decision to stop an agent. 
		case STOP: 		what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::stop);break;
		
		// ***** Indicate a location where to go. 
		case MOVE_TO:	what2do = new DecisionToMove(this.orga, this.attribute);break;
		
		// ***** Choose randomly a location to go. 
		case MOVE_AWAY:	// ***** Choose randomly a location to go. 
			int direction	= DecisionToMove.getRandomDirection(this.orga.getDirection());
			this.orga.setDirection( direction );
			what2do			= new DecisionToMove(this.orga, direction);break;
		
		// ***** Getting an object / agent. 
		case GET: 		what2do = new BaseDecisionOnlyOneAttribute(this.orga, this.object) {
			@Override
			public void action() {
				Agent o	= this.getOrga().getCurrentWorldCase().getAgentType( AgentType.getFrom(this.getVariable(0)) );
				if ( (o != null) && (o.isMovable()) ) {
					this.getOrga().getCurrentWorldCase().remAgent( o );
					this.getOrga().addAgent( o );
					o.setCurrentWorldCase( null );
				} // END "if ( (o != null) && (o.isMovable()) )"
			}
		};break;
		
		// ***** Dropping an object / agent. 
		case DROP:		what2do = new BaseDecisionOnlyOneAttribute(this.orga, this.object) {
			@Override
			public void action() {
				AgentType at	= (this.getVariablesLength() >= 1) ? AgentType.getFrom(this.getVariable(0)) : null;
				StatusType st	= (this.getVariablesLength() >= 2) ? StatusType.getFrom(this.getVariable(1)) : null;
				if (at == null) { return; }
				Agent o1 = this.getOrga().getAgentType( at );
				Agent o2 = (st != null) ? this.getOrga().getAgentStatus( st ) : null;
				// if type is present and not status : drop by type ! Else priority to status (if not null). 
				Agent o = ((o1 != null) && (o2 == null))? o1 : (o2 != null)? o2 : (o1 != null) ? o1 : null;
				if ( (o != null) && (o.isMovable()) ) {
					this.getOrga().remAgent( o );
					o.setCurrentWorldCase(this.getOrga().getCurrentWorldCase());
				} // END "if ( (o != null) && (o.isMovable()) )"
			}
		};break;
		
		// ***** Change state of Agent.
		case THINK: 	what2do = new BaseDecisionOnlyOneAttribute(this.orga, this.object) {
			@Override
			public void action() {
				this.getOrga().think("think about [" + object + "]\t");
			}
		};break;
		
		// ***** Decision to slap an agent. 
		case SLAP: 		what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::slap);break;
		
		case REST: 		break; // ***** Nothing
		
		case SLEEP: 	break; // ***** XXX Not defined yet !
		
		// ***** Eating decision (on eatable object). 
		case EAT: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				AgentType objectType = AgentType.getFrom( object );
				if (this.getOrga().hasAgentType( objectType ) > threshold) {
					Agent eatableAgent = this.getOrga().getAgentType( objectType );
					this.getOrga().remAgent(eatableAgent);
					this.getOrga().getVariables().incorporate(eatableAgent.getVariables());
				}
			}
		};break;
		
		// ***** Death append. (ch941 set to 999, alife to false).
		case DEATH: 	what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				this.getOrga().setAlive( false ); 
				this.getOrga().getVariables().setVariable(variable, value);
			}
		};break;
		
		// ***** Emission action. 
		case EMIT: 	what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().getCurrentWorldCase() == null) { return; }
				
				WorldCase wcwc = this.getOrga().getCurrentWorldCase().getDirection(object);
				if (wcwc != null) { 
					wcwc.getVariables().setVarPlus(variable, value);
					this.getOrga().getVariables().setVarLess(variable, value);
				}
			}
		};break;
		
		// ***** Reception action. 
		case RECEIVE: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().getCurrentWorldCase() == null) { return; }
				
				WorldCase wcwc = this.getOrga().getCurrentWorldCase().getDirection(object);
				if ( (wcwc != null) && (wcwc.getVariables().getVariable(variable) > threshold) ) { 
					this.getOrga().getVariables().setVarPlus(variable, value);
					wcwc.getVariables().setVarLess(variable, value);
				}
			}
		};break;
		
		// ***** Internal detection of an object and an attribute type. 
		case HAS: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				AgentType objectType1 = AgentType.getFrom( object );
				AgentType objectType2 = AgentType.getFrom( attribute );
				if ( (this.getOrga().hasAgentType( objectType1 ) >= threshold)
						&& (this.getOrga().hasAgentType( objectType2 ) >= threshold) )
					{ this.getOrga().getVariables().setVarPlus(variable, value); }
			}
		};break;
		
		// ***** Action to set new type or state of current agent. 
		case IS: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				this.getOrga().getVariables().setVariable(variable, value);
			}
		};break;
		
		// **** Making Gamet. 
		case MAKE_GAMET:what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().isFertile()) {
					// TODO make real gamets !! (if apply)
					Organism gamet = ReproductionHelper.makeGamet(this.getOrga());
					if (gamet != null) {
						this.getOrga().addAgent(gamet);
					}
					// ***** Increase Gamet / make exact count !!
					this.getOrga().getChemicals().setVariable(SomeChemicals.GAMET.getIndex(), 
							this.getOrga().hasAgentStatus(StatusType.EGG));
					// TODO to change it / values !!
					// int agentTypeGamet = SomeChemicals.GAMET.getIndex();
					// this.getOrga().getVariables().setVarPlusPlus(agentTypeGamet);
				}
			}
		};break;
		
		// ***** Lay Egg. 
		case LAY_EGG: 	what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				// ***** Change status about egg contenant (pregnancy). 
				this.getOrga().getVariables().setVariable(StateType.PREGNANT.getIndex(), this.getOrga().hasAgentStatus( StatusType.EGG ));
				if (this.getOrga().isPregnant()) {
					// Drop :: Egg
					DecisionBuilder db	= new DecisionBuilder();
					IDecision decision	= db.type(DecisionType.DROP).organism(this.getOrga())
											.object( this.getOrga().getOrganismType() )
											.attribute( StatusType.EGG.getIndex() ).build();
					if (decision != null) { decision.action(); }
					this.getOrga().getVariables().setVariable(StateType.PREGNANT.getIndex(), this.getOrga().hasAgentStatus( StatusType.EGG ));
				}
			}
		};break;
		
		// ***** Mating. 
		case MATE: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				// ***** Gamets presence increases fertility signal. 
				this.getOrga().getVariables().setVarPlus(StateType.FERTILE.getIndex(), 		this.getOrga().getVariables().getVariable(SomeChemicals.EGG.getIndex()));
				// ***** Eggs presence decreases fertility signal. 
				this.getOrga().getVariables().setVarLess(StateType.FERTILE.getIndex(), 		this.getOrga().hasAgentStatus( StatusType.EGG ));
				if (this.getOrga().isFertile()) {
					
					AgentType otype = this.getOrga().getOrganismTypeAsType();
					if (otype == null) { return; }
					switch(otype) {
					case BIOSILICO_DAEMON:
						// ***** nothing definately defined yet (cloning ?!)
						ReproductionDaemon.getInstance().action(this.getOrga());
						break;
					case BIOSILICO_BACTER:
						// ***** create a duplicate ! (modulo duplication, deletion, mutations of genes... )
						ReproductionBacta.getInstance().action(this.getOrga());
						break;
					case BIOSILICO_VIRIDITA:
					case BIOSILICO_ANIMA:
						// ***** find mate (could be itself ? depending of sex ?)
						// ***** find another agent to mate ! (could be itself ?)
						List<Organism> maters = new ArrayList<Organism>();
						maters.add( this.getOrga() );
						// IntStream.range(0, this.variables.getVariable( 932 ) / 2)
						Agent futuremate = this.getOrga().getCurrentWorldCase().getAgentType( this.getOrga().getOrganismTypeAsType() );
						if ( (futuremate != null) && (futuremate instanceof Organism) ) {
							// Compatibility of reproduction is checked further. 
							maters.add( (Organism) futuremate );
						}
						if (otype.equals(AgentType.BIOSILICO_VIRIDITA)) {
							ReproductionViridita.getInstance().action( maters.toArray(new Organism[0]) );
						} else { // OrganismType.BIOSILICO_ANIMA
							ReproductionAnima.getInstance().action( maters.toArray(new Organism[0]) );
						}
						break;
					case BIOSILICO_VIRIA:
						// ***** no reproduction, only inside organism and create virions ?!
						// NOTE : create directly EGGs / GAMETs ?!
						break;
					}
				}
				// ***** Basic pregnancy signal : number of eggs. 
				this.getOrga().getVariables().setVariable(StateType.PREGNANT.getIndex(), 	this.getOrga().hasAgentStatus( StatusType.EGG ));
			}
		};break;
		case CREATE_EGG: 
			// ***** to create eggs, virions, fruits... which have to be "laid" !!
			// this.decisiontoCreateEgg();
		break;
		default:
			Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown / Unused Decision ! [" + this.type.getIndex() + "] {" + this.type.getName() + "}");
			// ***** XXX NOTE : 872 to 880 are free. 
		}
		
		return what2do;
	}
	
}
