import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFabric } from '../fabric.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fabric.test-samples';

import { FabricService, RestFabric } from './fabric.service';

const requireRestSample: RestFabric = {
  ...sampleWithRequiredData,
  buyDate: sampleWithRequiredData.buyDate?.format(DATE_FORMAT),
};

describe('Fabric Service', () => {
  let service: FabricService;
  let httpMock: HttpTestingController;
  let expectedResult: IFabric | IFabric[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FabricService);
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

    it('should create a Fabric', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fabric = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fabric).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Fabric', () => {
      const fabric = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fabric).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Fabric', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Fabric', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Fabric', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFabricToCollectionIfMissing', () => {
      it('should add a Fabric to an empty array', () => {
        const fabric: IFabric = sampleWithRequiredData;
        expectedResult = service.addFabricToCollectionIfMissing([], fabric);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabric);
      });

      it('should not add a Fabric to an array that contains it', () => {
        const fabric: IFabric = sampleWithRequiredData;
        const fabricCollection: IFabric[] = [
          {
            ...fabric,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFabricToCollectionIfMissing(fabricCollection, fabric);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Fabric to an array that doesn't contain it", () => {
        const fabric: IFabric = sampleWithRequiredData;
        const fabricCollection: IFabric[] = [sampleWithPartialData];
        expectedResult = service.addFabricToCollectionIfMissing(fabricCollection, fabric);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabric);
      });

      it('should add only unique Fabric to an array', () => {
        const fabricArray: IFabric[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fabricCollection: IFabric[] = [sampleWithRequiredData];
        expectedResult = service.addFabricToCollectionIfMissing(fabricCollection, ...fabricArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fabric: IFabric = sampleWithRequiredData;
        const fabric2: IFabric = sampleWithPartialData;
        expectedResult = service.addFabricToCollectionIfMissing([], fabric, fabric2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabric);
        expect(expectedResult).toContain(fabric2);
      });

      it('should accept null and undefined values', () => {
        const fabric: IFabric = sampleWithRequiredData;
        expectedResult = service.addFabricToCollectionIfMissing([], null, fabric, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabric);
      });

      it('should return initial array if no Fabric is added', () => {
        const fabricCollection: IFabric[] = [sampleWithRequiredData];
        expectedResult = service.addFabricToCollectionIfMissing(fabricCollection, undefined, null);
        expect(expectedResult).toEqual(fabricCollection);
      });
    });

    describe('compareFabric', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFabric(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFabric(entity1, entity2);
        const compareResult2 = service.compareFabric(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFabric(entity1, entity2);
        const compareResult2 = service.compareFabric(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFabric(entity1, entity2);
        const compareResult2 = service.compareFabric(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
