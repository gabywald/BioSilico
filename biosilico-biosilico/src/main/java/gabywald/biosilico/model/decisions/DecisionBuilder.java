package gabywald.biosilico.model.decisions;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.interfaces.IAgentContent;
import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.reproduction.ReproductionAnima;
import gabywald.biosilico.model.reproduction.ReproductionBacta;
import gabywald.biosilico.model.reproduction.ReproductionDaemon;
import gabywald.biosilico.model.reproduction.ReproductionHelper;
import gabywald.biosilico.model.reproduction.ReproductionViridita;
import gabywald.global.structures.StringCouple;
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
		
		// ***** Decision to slap an agent. 
		case SLAP: 		what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::slap);break;
		
		// ***** Decision to make and agent rest (self). 
		case REST: 		what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::rest);break;
		
		// ***** Decision to make and agent sleep (self). 
		case SLEEP: 	what2do = new BaseDecisionSimpleAction<Agent>(this.orga, this.object, Agent::sleep);break;
		
		// ***** Indicate a location where to go. 
		case MOVE_TO:	what2do = new DecisionToMove(this.orga, this.attribute);break;
		
		// ***** Choose randomly a location to go. 
		case MOVE_AWAY:	
			DirectionWorld direction	= DecisionToMove.getRandomDirection2D(this.orga.getDirection());
			while (direction == DirectionWorld.CURRENT) {
				direction = DecisionToMove.getRandomDirection2D(this.orga.getDirection());
			}
			this.orga.setDirection( direction );
			what2do			= new DecisionToMove(this.orga, direction.getIndex());break;
		
		// ***** Getting an object / agent. 
		case GET: 		what2do = new BaseDecisionOnlyOneAttribute(this.orga, this.object) {
			@Override
			public void action() {
				Agent obj	= this.getOrga().getCurrentEnvironmentItem().getObjectType( ObjectType.getFrom(this.getVariable(0)) );
				if (obj == null) { return; }
				if ( (obj != null) && (obj.isMovable()) ) {
					this.getOrga().getCurrentEnvironmentItem().remAgent( obj );
					this.getOrga().addAgent( obj );
					obj.setCurrentWorldCase( null );
				} // END "if ( (o != null) && (o.isMovable()) )"
			}
		};break;
		
		// ***** Dropping an object / agent. 
		case DROP:		what2do = new BaseDecision(this.orga, this.object, this.attribute) {
			@Override
			public void action() {
				ObjectType ot	= (this.getVariablesLength() >= 1) ? ObjectType.getFrom(this.getVariable(0)) : null;
				StatusType st	= (this.getVariablesLength() >= 2) ? StatusType.getFrom(this.getVariable(1)) : null;
				if (ot == null) { return; }
				Agent o1 = this.getOrga().getObjectType( ot );
				Agent o2 = (st != null) ? this.getOrga().getAgentStatus( st ) : null;
				// if type is present and not status : drop by type ! Else priority to status (if not null). 
				Agent o = ((o1 != null) && (o2 == null))? o1 : (o2 != null)? o2 : (o1 != null) ? o1 : null;
				if ( (o != null) && (o.isMovable()) ) {
					this.getOrga().remAgent( o );
					o.setCurrentWorldCase(this.getOrga().getCurrentEnvironmentItem());
				} // END "if ( (o != null) && (o.isMovable()) )"
			}
		};break;
		
		// ***** Change state of Agent.
		case THINK: 	what2do = new BaseDecisionOnlyOneAttribute(this.orga, this.object) {
			@Override
			public void action() {
				try {
					StringCouple scObject = ChemicalsHelper.getChemicalListe().get( object );  // indication
					this.getOrga().think("think about [" + scObject.getValueA() + "]\t");
				} catch (IndexOutOfBoundsException iobe) {
					Logger.printlnLog(LoggerLevel.LL_ERROR, "DECISION THINK called with object [" + object + "]");
					this.getOrga().think("think about [" + object + "]\t");
				}
			}
		};break;
		
		// ***** Eating decision (on eatable object). 
		case EAT: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				ObjectType type = ObjectType.getFrom( object );
				if (this.getOrga().hasObjectType( type ) > threshold) {
					Agent eatableAgent = this.getOrga().getObjectType( type );
					if ( ! eatableAgent.isEatable()) { ; }
					this.getOrga().remAgent(eatableAgent);
					this.getOrga().getChemicals().incorporate(eatableAgent.getChemicals());
				}
			}
		};break;
		
		// ***** Death append. (ch941 set to 999, alife to false).
		case DEATH: 	what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().getChemicals().getVariable(attribute) > threshold) {
					this.getOrga().setAlive( false ); 
					this.getOrga().setOrganismStatus(StatusType.DEAD);
					this.getOrga().getChemicals().setVariable(variable, value);
				}
			}
		};break;
		
		// ***** Emission action. 
		case EMIT: 	what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().getCurrentEnvironmentItem() == null) { return; }
				
				DirectionWorld direction = DirectionWorld.get2DFrom( object );
				if (direction == null)		{ return; }
				
				IEnvironmentItem wcwc = this.getOrga().getCurrentEnvironmentItem().getDirection( direction );
				if ( (wcwc != null) && (this.getOrga().getChemicals().getVariable(variable) >= value) ) { 
					wcwc.getChemicals().setVarPlus(variable, value);
					this.getOrga().getChemicals().setVarLess(variable, value);
				}
			}
		};break;
		
		// ***** Reception action. 
		case RECEIVE: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().getCurrentEnvironmentItem() == null) { return; }
				
				DirectionWorld direction = DirectionWorld.get2DFrom( object );
				if (direction == null)		{ return; }
				
				IEnvironmentItem wcwc = this.getOrga().getCurrentEnvironmentItem().getDirection( direction ); // indication
				if ( (wcwc != null) && (wcwc.getChemicals().getVariable(variable) >= value) ) { 
					this.getOrga().getChemicals().setVarPlus(variable, value);
					wcwc.getChemicals().setVarLess(variable, value);
				}
			}
		};break;
		
		// ***** Internal detection of an object and an attribute type. 
		case HAS: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				AgentType agentType		= AgentType.getFrom( attribute );
				ObjectType objectType	= ObjectType.getFrom( object ); // indication
				if (agentType == null)	{ return; }
				if (objectType == null)	{ return; }
				if ( (this.getOrga().hasAgentType( agentType ) > threshold)
						&& (this.getOrga().hasObjectType( objectType ) > threshold) )
					{ this.getOrga().getChemicals().setVarPlus(variable, value); }
			}
		};break;
		
		// ***** Action to set new type or state of current agent. 
		case IS: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				this.getOrga().getChemicals().setVariable(variable, value);
			}
		};break;
		
		// **** Making Gamet. 
		case MAKE_GAMET:what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				if (this.getOrga().isFertile()) {
					Organism gamet = ReproductionHelper.makeGamet(this.getOrga());
					gamet.setOrganismStatus(StatusType.GAMET);
					ObjectType ot = ObjectType.getFrom( attribute );
					if (ot != null) { gamet.setObjectType( ot ); } 
					if (gamet != null) {
						this.getOrga().addAgent(gamet);
					}
					// ***** Increase Gamet / make exact count !!
					this.getOrga().getChemicals().setVariable(	StatusType.GAMET.getIndex(), 
																this.getOrga().hasAgentStatus(StatusType.GAMET));
				}
				
				// Logger.printlnLog(LoggerLevel.LL_FORUSER, "MAKE_GAMET" ); 
			}
			
		};break;
		
		// ***** Lay Egg. I.E. 'DROP EGG' !
		case LAY_EGG: 	what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				// ***** Change status about egg contenant (pregnancy). 
				this.getOrga().getChemicals().setVariable(StateType.PREGNANT.getIndex(), this.getOrga().hasAgentStatus( StatusType.EGG ));
				// ***** If Apply => lay effectively egg !!
				if (this.getOrga().isPregnant()) {
					// Drop :: Egg
					DecisionBuilder db	= new DecisionBuilder();
					// XXX NOTE beware here for Object type / Agent Type : semantic meaning (idea of fusion of AgentType / ObjectType ?!) ... 
					IDecision decision	= db.type(DecisionType.DROP).organism(this.getOrga())
											.object( this.getOrga().getObjectType().getIndex() )
											.attribute( StatusType.EGG.getIndex() ).build();
					if (decision != null) { decision.action(); }
					this.getOrga().getChemicals().setVariable(	StateType.PREGNANT.getIndex(), 
																this.getOrga().hasAgentStatus( StatusType.EGG ));
				}
			}
		};break;
		
		// ***** Mating. 
		case MATE: 		what2do = new BaseDecision(this.orga) {
			@Override
			public void action() {
				// ***** Gamets presence increases fertility signal. 
				this.getOrga().getChemicals().setVarPlus(StateType.FERTILE.getIndex(), 	this.getOrga().getChemicals().getVariable(StatusType.GAMET.getIndex()));
				// ***** Eggs presence decreases fertility signal (impact pregnancy). 
				this.getOrga().getChemicals().setVarLess(StateType.FERTILE.getIndex(), 	this.getOrga().hasAgentStatus( StatusType.EGG ));
				this.getOrga().getChemicals().setVarPlus(StateType.PREGNANT.getIndex(), this.getOrga().hasAgentStatus( StatusType.EGG ));
				if (this.getOrga().isFertile()) {
					AgentType otype = this.getOrga().getAgentType();
					if (otype == null) { return; }
					switch(otype) {
					case BIOSILICO_DAEMON:
						// ***** nothing definately defined yet (cloning ?!)
						ReproductionDaemon.getInstance().action(this.getOrga());
						break;
					case BIOSILICO_BACTA:
						// ***** create a duplicate ! (modulo duplication, deletion, mutations of genes... )
						ReproductionBacta.getInstance().action(this.getOrga());
						break;
					case BIOSILICO_VIRIDITA:
					case BIOSILICO_ANIMA:
						// ***** find mate (could be itself ? depending of sex ?)
						// ***** find another agent to mate ! (could be itself ?)
						List<Organism> maters = new ArrayList<Organism>();
						maters.add( this.getOrga() );

						Logger.printlnLog(LoggerLevel.LL_DEBUG, "AgentType {" + this.getOrga().getAgentType() + "} available: (" + this.getOrga().getCurrentEnvironmentItem().hasAgentType( this.getOrga().getAgentType() ) + ")");
						
						// ***** If more than one available : do not select the same ! 
						List<Agent> availables = IAgentContent.getListOfType(otype, StateType.AGENT_TYPE.getIndex(), this.getOrga().getCurrentEnvironmentItem().getAgentListe());
						int index = 0;
						Agent futuremate = availables.get(0); 
						while ( (availables.size() > 1) 
									&& (this.getOrga().getUniqueID().equals(futuremate.getUniqueID())) ) 
							{ futuremate = availables.get(++index); }
						
						if ( this.getOrga().getUniqueID().equals(futuremate.getUniqueID()) )
							{ futuremate = null; }

						// ***** Compatibility of reproduction is checked later.
						if ( (futuremate != null) && (futuremate instanceof Organism) ) {
							maters.add( (Organism) futuremate );
						}
						
						Logger.printlnLog(LoggerLevel.LL_DEBUG, "DB.build {" + this.getOrga().getUniqueID() + "}-{" + maters.get(0).getUniqueID() + "}");
						
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
				this.getOrga().getChemicals().setVariable(StateType.PREGNANT.getIndex(), 	this.getOrga().hasAgentStatus( StatusType.EGG ));
			}
		};break;
		// ***** to create eggs, virions, fruits... which have to be "laid" !!
		// XXX NOTE see MATE decision !
		default:
			Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown / Unused Decision ! [" + this.type.getIndex() + "] {" + this.type.getName() + "}");
			// ***** XXX NOTE : 872 to 880 are free. 
		}
		
		return what2do;
	}
	
}
