drop table voeux;
drop table formation;
drop table administrateur;
drop table etudiant;

--Creation des tables
create table etudiant(identifiant varchar2(8), nom varchar2(50), prenom varchar2(50), groupe number, mail varchar2(50), passwd varchar2(40));
create table administrateur(identifiant varchar2(8), nom varchar2(50), prenom varchar2(50), passwd varchar2(20));
create table formation(numero number, ecole varchar2(100), formation varchar2(50), alternance char(3), ville varchar2(20), siteWeb varchar2(255), dateLimite date);
create table voeux(identifiant varchar2(8), numero number, ordre number, avis varchar2(20) default 'Pas d avis');

--Creation des clefs
alter table etudiant add constraint PK_idEtu Primary Key (identifiant);
alter table administrateur add constraint PK_idAdmin Primary Key (identifiant);
alter table formation add constraint PK_num Primary Key (numero);
alter table formation add constraint alternance_bool check (alternance in ('Oui', 'Non'));
alter table voeux add constraint PK_voeux Primary Key (identifiant, numero);
alter table voeux add constraint FK_idEtu Foreign Key (identifiant) references etudiant(identifiant);
alter table voeux add constraint FK_numForm Foreign Key (numero) references formation(numero);

--Declencheurs

  --Trigger pour eviter qu'une valeur soit nulle chez les etudiants
create or replace trigger createtudiant after insert or update on etudiant
declare REC_etudiant etudiant%RowType;
begin
  select * into REC_etudiant from etudiant
    where groupe is null or nom is null or prenom is null or passwd is null or mail is null;
  RAISE_APPLICATION_ERROR(-20001,'Erreur ! Vous devez entrer toutes les informations sur vous (a savoir : groupe, nom, prenom, identifiant, mot de passe)');
  exception
    when no_data_found then null;
end;

  --Trigger pour eviter qu'une valeur soit nulle chez les administrateurs
create or replace trigger creatadministrateur after insert or update on administrateur
declare REC_administrateur administrateur%RowType;
begin
  select * into REC_administrateur from administrateur
    where nom is null or prenom is null or passwd is null;
  RAISE_APPLICATION_ERROR(-20003,'Erreur ! Vous devez entrer toutes les informations sur vous (a savoir : nom, prenom, identifiant, mot de passe)');
  exception
    when no_data_found then null;
end;

  --Trigger pour eviter qu'une valeur soit nulle dans les formations
create or replace trigger creatformation after insert or update on formation
declare REC_formation formation%RowType;
begin
  select * into REC_formation from formation
    where ville is null or siteWeb is null or formation is null or ecole is null or dateLimite is null or alternance is null;
  RAISE_APPLICATION_ERROR(-20002,'Erreur ! Vous devez entrer toutes les informations sur la formation (a savoir : ecole, ville, site Web, etudiant, numero)');
  exception
    when no_data_found then null;
end;

--Procedure-Package

CREATE OR REPLACE PACKAGE package_creation AS
  Procedure creer_etudiant(identifiant varchar2, nom varchar2, prenom varchar2, groupe number, mail varchar2, passwd varchar2);
  Procedure creer_admin(identifiant varchar2, nom varchar2, prenom varchar2, passwd varchar2);
  Procedure creer_formation(numero number, ecole varchar2, formation varchar2, alternance char, ville varchar2, siteWeb varchar2, dateLimite varchar2);
  Procedure creer_voeux(identifiant varchar2, num number, ordre number);
END;

CREATE OR REPLACE PACKAGE BODY package_creation AS
  Procedure creer_etudiant(identifiant varchar2, nom varchar2, prenom varchar2, groupe number, mail varchar2, passwd varchar2) is
  Begin
    SET TRANSACTION Read write;
    insert into etudiant values(identifiant, nom, prenom, groupe, mail, passwd);
    commit;
    Exception
      when others then
        if sqlcode = -0001 then rollback;
          raise_application_error(-20010, 'Etudiant deja existant');
        elsif sqlcode = -1438 then rollback;
          raise_application_error(-20011, 'Taille du champ non respectee');
        elsif sqlcode = -12899 then rollback;
          raise_application_error(-20012, 'Taille du champ non respect�e');
        elsif sqlcode = -20001 then rollback;
          raise_application_error(-20013, 'Erreur ! Vous devez entrer toutes les informations sur vous (� savoir : groupe, nom, prenom, identifiant, mot de passe)');
        else rollback;
          raise_application_error(-20014, 'Erreur inattendue');
        end if;
  End;
  Procedure creer_admin(identifiant varchar2, nom varchar2, prenom varchar2, passwd varchar2) is
  Begin
    set transaction Read write;
    insert into administrateur values(identifiant, prenom, nom, passwd);
    commit;
    Exception
      when others then
        if sqlcode = -0001 then rollback;
          raise_application_error(-20015, 'Administrateur d�j� existant');
        elsif sqlcode = -1438 then rollback;
          raise_application_error(-20016, 'Taille du champ non respect�e');
        elsif sqlcode = -12899 then rollback;
          raise_application_error(-20017, 'Taille du champ non respect�e');
        elsif sqlcode = -20003 then rollback;
          raise_application_error(-20018, 'Erreur ! Vous devez entrer toutes les informations sur vous (� savoir : nom, prenom, identifiant, mot de passe)');
        else rollback;
          raise_application_error(-20019, 'Erreur inattendue');
        end if;
  End;
  Procedure creer_formation(numero number, ecole varchar2, formation varchar2, alternance char, ville varchar2, siteWeb varchar2, dateLimite varchar2) is
  Begin
    set transaction Read write;
    insert into formation values(numero, ecole, formation, alternance, ville, siteWeb, to_date(dateLimite, 'DD-MM-YY'));
    commit;
    Exception
      when others then
        if sqlcode = -0001 then rollback;
          raise_application_error(-20020, 'Formation d�j� existante');
        elsif sqlcode = -1438 then rollback;
          raise_application_error(-20021, 'Taille du champ non respect�e');
        elsif sqlcode = -2290 then rollback;
          raise_application_error(-20031, 'Entrez oui ou non');
        elsif sqlcode = -12899 then rollback;
          raise_application_error(-20022, 'Taille du champ non respect�e');
        elsif sqlcode = -20002 then rollback;
          raise_application_error(-20023, 'Erreur ! Vous devez entrer toutes les informations sur la formation (� savoir : ville, site Web, etudiant, numero)');
        else rollback;
          raise_application_error(-20024, 'Erreur inattendue');
        end if;
  end;
  Procedure creer_voeux(identifiant varchar2, num number, ordre number) is
    voeuFini Exception;
    dateFin date;
    Begin
      set transaction Read write;
      select dateLimite into dateFin from formation f where f.numero = num;
      if (dateFin < sysdate()) then
        raise voeuFini; 
      else 
        insert into voeux values(identifiant, num, ordre, default);
      end if;
      commit;
      Exception
        when voeuFini then rollback;
            raise_application_error(-20030, 'Voeu impossible, les inscriptions pour cette formation sont termin�es.');
        when others then
          if sqlcode = -0001 then rollback;
            raise_application_error(-20025, 'Voeu d�j� existant');
          elsif sqlcode = -2291 then rollback;
            raise_application_error(-20026, 'La formation (ou vous) n''existe pas');
          elsif sqlcode = -1438 then rollback;
            raise_application_error(-20027, 'Taille du champ non respect�e');
          elsif sqlcode = -12899 then rollback;
            raise_application_error(-20028, 'Taille du champ non respect�e');
          else rollback;
            raise_application_error(-20029, 'Erreur inattendue');
          end if;
  End;
END;

CREATE OR REPLACE PACKAGE package_suppression AS
  Procedure suppr_etudiant(iden varchar2);
  Procedure suppr_formation(num_form number);
  Procedure suppr_voeux(iden varchar2, num number);
END;

CREATE OR REPLACE PACKAGE BODY package_suppression AS
  Procedure suppr_etudiant(iden varchar2) is
    voeuxPresent Exception;
    nbrVoeux pls_integer;
    Begin
      set transaction read write;
      select count(*) into nbrVoeux from voeux where identifiant = iden;
      if (nbrVoeux > 0) then raise voeuxPresent;
      end if;
      delete from etudiant where identifiant = iden;
      commit;
      Exception
        when voeuxPresent then
          delete from voeux where identifiant = iden;
          delete from etudiant where identifiant = iden;
          commit;
  End;
  Procedure suppr_formation(num_form number) is
    voeuxPresent Exception;
    nbrVoeux pls_integer;
    Begin
      set transaction read write;
      select count(*) into nbrVoeux from voeux where numero = num_form;
      if (nbrVoeux > 0) then raise voeuxPresent;
      end if;
      delete from formation where numero = num_form;
      commit;
      Exception
      when voeuxPresent then
          delete from voeux where numero = num_form;
          delete from formation where numero = num_form;
          commit;
  End;
  Procedure suppr_voeux(iden varchar2, num number) is
    Begin
      set transaction read write;
      delete from voeux where identifiant = iden and numero = num;
      commit;
    end;
END;

CREATE OR REPLACE PACKAGE package_info AS
  Type CUR_ETU is REF CURSOR;
  Procedure getInfoEtu(iden varchar2, infoEtu out CUR_ETU);
  Procedure setAvis(iden varchar2, ord number, avisSur varchar2);
END;

CREATE OR REPLACE PACKAGE BODY package_info AS
  Procedure getInfoEtu(iden varchar2, infoEtu out CUR_ETU) is
    Begin
      SET TRANSACTION READ ONLY;
      OPEN infoEtu for select f.numero, f.formation, f.ecole, f.ville, v.avis
        from formation f, etudiant e, voeux v
        where e.identifiant = iden and f.NUMERO = v.NUMERO
        and e.IDENTIFIANT = v.IDENTIFIANT 
        group by f.numero, f.formation, f.ecole, f.ville, v.avis
        order by f.numero;
      COMMIT;
    Exception
      when others then ROLLBACK;
        RAISE_APPLICATION_ERROR(-20031, 'Erreur inattendue');
  End;
  Procedure setAvis(iden varchar2, ord number, avisSur varchar2) is
  Begin
    update voeux set avis = avisSur
      where identifiant = iden and ordre = ord;
    COMMIT;
    Exception
      when others then ROLLBACK;
        RAISE_APPLICATION_ERROR(-20032, 'Erreur inattendue');
  End;
END;

insert into administrateur values('Admin', 'Admin', 'Admin', 'admin')