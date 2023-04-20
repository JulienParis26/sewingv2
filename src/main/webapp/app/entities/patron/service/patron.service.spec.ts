import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPatron } from '../patron.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../patron.test-samples';

import { PatronService, RestPatron } from './patron.service';

const requireRestSample: RestPatron = {
  ...sampleWithRequiredData,
  buyDate: sampleWithRequiredData.buyDate?.format(DATE_FORMAT),
  publicationDate: sampleWithRequiredData.publicationDate?.format(DATE_FORMAT),
};

describe('Patron Service', () => {
  let service: PatronService;
  let httpMock: HttpTestingController;
  let expectedResult: IPatron | IPatron[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PatronService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Patron', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const patron = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(patron).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Patron', () => {
      const patron = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(patron).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Patron', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Patron', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Patron', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPatronToCollectionIfMissing', () => {
      it('should add a Patron to an empty array', () => {
        const patron: IPatron = sampleWithRequiredData;
        expectedResult = service.addPatronToCollectionIfMissing([], patron);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patron);
      });

      it('should not add a Patron to an array that contains it', () => {
        const patron: IPatron = sampleWithRequiredData;
        const patronCollection: IPatron[] = [
          {
            ...patron,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPatronToCollectionIfMissing(patronCollection, patron);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Patron to an array that doesn't contain it", () => {
        const patron: IPatron = sampleWithRequiredData;
        const patronCollection: IPatron[] = [sampleWithPartialData];
        expectedResult = service.addPatronToCollectionIfMissing(patronCollection, patron);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patron);
      });

      it('should add only unique Patron to an array', () => {
        const patronArray: IPatron[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const patronCollection: IPatron[] = [sampleWithRequiredData];
        expectedResult = service.addPatronToCollectionIfMissing(patronCollection, ...patronArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const patron: IPatron = sampleWithRequiredData;
        const patron2: IPatron = sampleWithPartialData;
        expectedResult = service.addPatronToCollectionIfMissing([], patron, patron2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patron);
        expect(expectedResult).toContain(patron2);
      });

      it('should accept null and undefined values', () => {
        const patron: IPatron = sampleWithRequiredData;
        expectedResult = service.addPatronToCollectionIfMissing([], null, patron, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patron);
      });

      it('should return initial array if no Patron is added', () => {
        const patronCollection: IPatron[] = [sampleWithRequiredData];
        expectedResult = service.addPatronToCollectionIfMissing(patronCollection, undefined, null);
        expect(expectedResult).toEqual(patronCollection);
      });
    });

    describe('comparePatron', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePatron(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePatron(entity1, entity2);
        const compareResult2 = service.comparePatron(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePatron(entity1, entity2);
        const compareResult2 = service.comparePatron(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePatron(entity1, entity2);
        const compareResult2 = service.comparePatron(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
