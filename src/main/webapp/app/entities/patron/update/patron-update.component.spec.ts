import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PatronFormService } from './patron-form.service';
import { PatronService } from '../service/patron.service';
import { IPatron } from '../patron.model';

import { PatronUpdateComponent } from './patron-update.component';

describe('Patron Management Update Component', () => {
  let comp: PatronUpdateComponent;
  let fixture: ComponentFixture<PatronUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let patronFormService: PatronFormService;
  let patronService: PatronService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PatronUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PatronUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PatronUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    patronFormService = TestBed.inject(PatronFormService);
    patronService = TestBed.inject(PatronService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const patron: IPatron = { id: 456 };

      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      expect(comp.patron).toEqual(patron);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPatron>>();
      const patron = { id: 123 };
      jest.spyOn(patronFormService, 'getPatron').mockReturnValue(patron);
      jest.spyOn(patronService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: patron }));
      saveSubject.complete();

      // THEN
      expect(patronFormService.getPatron).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(patronService.update).toHaveBeenCalledWith(expect.objectContaining(patron));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPatron>>();
      const patron = { id: 123 };
      jest.spyOn(patronFormService, 'getPatron').mockReturnValue({ id: null });
      jest.spyOn(patronService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ patron: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: patron }));
      saveSubject.complete();

      // THEN
      expect(patronFormService.getPatron).toHaveBeenCalled();
      expect(patronService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPatron>>();
      const patron = { id: 123 };
      jest.spyOn(patronService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(patronService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
